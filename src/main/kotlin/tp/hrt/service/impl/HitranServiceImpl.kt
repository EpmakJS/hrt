package tp.hrt.service.impl


import org.knowm.xchart.QuickChart
import org.knowm.xchart.SwingWrapper
import org.springframework.stereotype.Service
import tp.hrt.createSingleLane
import tp.hrt.dto.*
import tp.hrt.service.HitranService
import javax.validation.UnexpectedTypeException
import kotlin.math.*

@Service
class HitranServiceImpl(
) : HitranService {

    override fun drawPlot(directTaskDto: DirectTaskDto): Int {
        val xy = createXY(directTaskDto.vmin, directTaskDto.vmax)
        val xData: DoubleArray = xy[0]
        var yData: DoubleArray = xy[1]

        for (i in 0..(dataBaseMock.size - 1)) {
            yData = createSingleLine(
                xData = xData,
                yData = yData,
                // Return from repo
                v = dataBaseMock[i].v,
                S_Tref = dataBaseMock[i].S_Tref,
                A = dataBaseMock[i].A,
                yair = dataBaseMock[i].yair,
                yself = dataBaseMock[i].yself,
                E2shtreha = dataBaseMock[i].E2shtreha,
                n = dataBaseMock[i].n,
                betta = dataBaseMock[i].betta,
                p = directTaskDto.p,
                T = directTaskDto.T,
                l = directTaskDto.l,
                concentration = getConcentration(dataBaseMock[i], directTaskDto.concentration),
                M = getMolarMass(dataBaseMock[i])
            )
        }
        plot(xData = xData, yData = yData)
        return findConcentration(xData, yData, xy[1], directTaskDto)
    }

    override fun findConcentration(x: DoubleArray, y: DoubleArray, emptyY: DoubleArray, directTaskDto: DirectTaskDto): Int {
        //обратная задача
        var yDataPercent: DoubleArray
        val percentStep = 0.001
        var percent = 0.0
        var sum: Double
        var sumPrev = 0.0
        while (percent < 1.0) {
            sum = 0.0
            yDataPercent = emptyY
            for (i in 0..9) {
                yDataPercent = createSingleLine(
                    xData = x,
                    yData = yDataPercent,
                    // Return from repo
                    v = dataBaseMock[i].v,
                    S_Tref = dataBaseMock[i].S_Tref,
                    A = dataBaseMock[i].A,
                    yair = dataBaseMock[i].yair,
                    yself = dataBaseMock[i].yself,
                    E2shtreha = dataBaseMock[i].E2shtreha,
                    n = dataBaseMock[i].n,
                    betta = dataBaseMock[i].betta,
                    p = directTaskDto.p,
                    T = directTaskDto.T,
                    l = directTaskDto.l,
                    concentration = getConcentration(dataBaseMock[i], directTaskDto.concentration),
                    M = getMolarMass(dataBaseMock[i])
                )
            }
            for (i in 1..(y.size - 1)) {
                sum += abs(y[i] - yDataPercent[i])
            }
            if (!percent.equals(0.0)  && sum - sumPrev > 0) {
                return floor((percent - percentStep) * 100.0).toInt()
            }
            sumPrev = sum
            percent += percentStep
        }

        return 0
    }

    private fun getMolarMass( dataBaseMock: Line): Double {
        return when (dataBaseMock) {
            is H2O -> molarMass.H2O
            is CO2 -> molarMass.CO2
            is O2 -> molarMass.O2
            is N2 -> molarMass.N2
            else -> throw UnexpectedTypeException()
        }
    }

    private fun getConcentration(dataBaseMock: Line, concentration: ConcentrationMock): Double {
        return when (dataBaseMock) {
            is H2O -> concentration.H2O
            is CO2 -> concentration.CO2
            is O2 -> concentration.O2
            is N2 -> concentration.N2
            else -> throw UnexpectedTypeException()
        }
    }

    private fun createXY(xMin: Double, xMax: Double): Array<DoubleArray> {
        val xStep = 0.01
        var xNext = xMin
        var plotX: DoubleArray = doubleArrayOf()
        var plotY: DoubleArray = doubleArrayOf()
        while (xNext < xMax) {
            plotX += doubleArrayOf(xNext)
            xNext += xStep
            plotY += doubleArrayOf(0.0)
        }
        return arrayOf(plotX, plotY)
    }

    private fun createSingleLine(xData: DoubleArray, yData: DoubleArray, v: Double, S_Tref: Double, A: Double, yair: Double,
                                 yself: Double, E2shtreha: Double, n: Double, betta: Double, p: Double,
                                 T: Double, l: Double, concentration: Double, M: Double): DoubleArray {
        //1)
        //Не могу раccчитать, временно не учитываем
        val Q_Tref: Double = 1.0 //заменить на Q(Tref)
        val Q_T: Double = 1.0 //заменить на Q(T)
        val S_T: Double = S_T(S_Tref = S_Tref, Q_Tref = Q_Tref, Q_T = Q_T, E2shtreha = E2shtreha, T = T, v = v)
        if (p > 0.15) {
            //2)
            val pself: Double = concentration * p
            val y_p_T: Double = y_p_T(p = p, T = T, pself = pself, yair = yair, yself = yself, n = n)
            //3)
            val vCorrected = vCorrected(v = v, betta = betta, p = p)
            //4)
            var newY: DoubleArray = doubleArrayOf()
            for (i in 0..(xData.size - 1)) {
                newY += doubleArrayOf(S_T * l * fL(x = xData[i], y_p_T = y_p_T, v_corrected = vCorrected) + yData[i])
            }
            return newY
        } else {
            //2)
            //M - molar mass (еще нет таблицы)
            val aD_T: Double = aD_T(T = T, v = v, M = M)
            //3)
            var newY: DoubleArray = doubleArrayOf()
            for (i in 0..(xData.size - 1)) {
                newY += doubleArrayOf(S_T * l * fG(x = xData[i], aD_T = aD_T, v = v) + yData[i])
            }
            return newY
        }
    }

    private fun plot(xData: DoubleArray, yData: DoubleArray) {
        val chart = QuickChart.getChart("H2O", "Wave Number", "Intensity", "S(V)", xData, yData)
        SwingWrapper(chart).displayChart()
        // Save it
        //BitmapEncoder.saveBitmap(chart, "./Sample_Chart", BitmapFormat.PNG)
        // or save it in high-res
        //BitmapEncoder.saveBitmapWithDPI(chart, "./Sample_Chart_300_DPI", BitmapFormat.PNG, 300)
    }

    //формула 1
    //Temperature dependence of the line intensity
    private fun S_T(S_Tref: Double, Q_Tref: Double, Q_T: Double, E2shtreha: Double, T: Double, v: Double) =
        S_Tref * (Q_Tref / Q_T) * (exp(-C2 * E2shtreha / T) / exp(-C2 * E2shtreha / T_REF)) * ((1 - exp(-C2 * v / T)) / (1 - exp(-C2 * v / T_REF)))

    //формула 2
    //Temperature and pressure dependence of the line width
    private fun aD_T(T: Double, v: Double, M: Double) = (v / C) * sqrt(2.0 * NA * K * T * ln(2.0) / M)

    //формула 3
    //γ(p,T) for a gas at pressure p (atm), temperature T (K) and partial pressure pself (atm)
    private fun y_p_T(p: Double, T: Double, pself: Double, yair: Double, yself: Double, n: Double) = ((T_REF / T).pow(n) * (yair * (p - pself) + yself * pself))

    //формула 4
    //Pressure shift correction of line position
    private fun vCorrected(v: Double, betta: Double, p: Double) = v + betta * p

    //формула 5
    //Absorption coefficient
    //Для нижних слоев атмосферы
    private fun fL(x: Double, y_p_T: Double, v_corrected: Double) = (1 / PI) * (y_p_T / (y_p_T.pow(2.0) + (x - v_corrected).pow(2.0)))

    //Для верхних слоев атмосферы
    private fun fG(x: Double, aD_T: Double, v: Double) =
        sqrt(ln(2.0) / (PI * (aD_T.pow(2.0)))) * exp(-((x - v).pow(2.0)) * ln(2.0) / aD_T.pow(2.0))

    companion object {
        const val NA: Double = 6.02214129e23
        const val K: Double = 1.3806488e-16 // (erg/K)
        const val H: Double = 6.62606957e-27 // (erg/K)
        const val C: Double = 2.99792458e10 // (cm/s)
        const val C2: Double = 1.4387770 // (cm*K)
        const val T_REF: Double = 296.0 // default
        const val P_REF: Double = 1.0 // default

        val molarMass = MolarMassMock()
    }
}