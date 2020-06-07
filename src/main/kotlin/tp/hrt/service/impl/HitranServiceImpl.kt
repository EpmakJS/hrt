package tp.hrt.service.impl

import org.knowm.xchart.QuickChart
import org.knowm.xchart.SwingWrapper
import org.springframework.stereotype.Service
import tp.hrt.dto.CO2
import tp.hrt.dto.ConcentrationMock
import tp.hrt.dto.DirectTaskDto
import tp.hrt.dto.H2O
import tp.hrt.dto.Line
import tp.hrt.dto.MolarMassMock
import tp.hrt.dto.O2
import tp.hrt.model.lines.LineType
import tp.hrt.model.lines.LineType.CH3CN
import tp.hrt.model.lines.LineType.CS
import tp.hrt.model.lines.LineType.N2
import tp.hrt.model.lines.LineType.NO_PLUS
import tp.hrt.model.lines.LineType.O
import tp.hrt.repository.CH3CNLineRepository
import tp.hrt.repository.CSLineRepository
import tp.hrt.repository.N2LineRepository
import tp.hrt.repository.NOPlusLineRepository
import tp.hrt.repository.OLineRepository
import tp.hrt.service.HitranService
import tp.hrt.service.MoleculeService
import javax.validation.UnexpectedTypeException
import kotlin.math.PI
import kotlin.math.exp
import kotlin.math.ln
import kotlin.math.pow
import kotlin.math.sqrt

@Service
class HitranServiceImpl(
    private val moleculeService: MoleculeService,
    private val n2LineRepository: N2LineRepository,
    private val cH3CNLineRepository: CH3CNLineRepository,
    private val cSLineRepository: CSLineRepository,
    private val nOPlusLineRepository: NOPlusLineRepository,
    private val oLineRepository: OLineRepository
) : HitranService {

    override fun getAbsorptionSpectrumOfGasMixture(directTaskDto: DirectTaskDto) {
        val emptyAbsorptionSpectrum = createEmptyAbsorptionSpectrum(directTaskDto.vmin, directTaskDto.vmax)
        val vacuumWavenumberData: DoubleArray = emptyAbsorptionSpectrum[0]
        var intensityData: DoubleArray = emptyAbsorptionSpectrum[1]

        directTaskDto.concentration.map { concentration ->
            val molecule = moleculeService.findMoleculeById(concentration.key)
            val repository = getAppropriateRepository(molecule.type)
            val lines = repository.findInRangeByVacuumWavenumber(directTaskDto.vmin, directTaskDto.vmax)

            lines?.forEach { line ->
                intensityData = getAbsorptionSpectrumOfSingleLine(
                    xData = vacuumWavenumberData,
                    yData = intensityData,
                    vacuumWavenumber = line.lineId.vacuumWavenumber,
                    S_Tref = line.lineId.intensity,
                    A = line.lineId.einsteinA,
                    yair = line.lineId.gammaAir,
                    yself = line.lineId.gammaSelf,
                    E2shtreha = line.lowerStateEnergy,
                    n = line.temperatureDependence,
                    betta = line.airPressure,
                    gasPressure = directTaskDto.p,
                    gasTemperature = directTaskDto.T,
                    gasColumnThickness = directTaskDto.l,
                    concentration = concentration.value,
                    molarMass = molecule.molarMass
                )
            }
        }

        plotAbsorptionSpectrum(vacuumWavenumberData = vacuumWavenumberData, intensityData = intensityData)
    }

//    override fun findConcentration(
//        x: DoubleArray,
//        y: DoubleArray,
//        emptyY: DoubleArray,
//        directTaskDto: DirectTaskDto
//    ): Int {
//        //обратная задача
//        var yDataPercent: DoubleArray
//        val percentStep = 0.001
//        var percent = 0.0
//        var sum: Double
//        var sumPrev = 0.0
//        while (percent < 1.0) {
//            sum = 0.0
//            yDataPercent = emptyY
//            for (i in 0..9) {
//                yDataPercent = createSingleLine(
//                    xData = x,
//                    yData = yDataPercent,
//                    // Return from repo
//                    v = dataBaseMock[i].v,
//                    S_Tref = dataBaseMock[i].S_Tref,
//                    A = dataBaseMock[i].A,
//                    yair = dataBaseMock[i].yair,
//                    yself = dataBaseMock[i].yself,
//                    E2shtreha = dataBaseMock[i].E2shtreha,
//                    n = dataBaseMock[i].n,
//                    betta = dataBaseMock[i].betta,
//                    p = directTaskDto.p,
//                    T = directTaskDto.T,
//                    l = directTaskDto.l,
//                    concentration = getConcentration(dataBaseMock[i], directTaskDto.concentration),
//                    M = getMolarMass(dataBaseMock[i])
//                )
//            }
//            for (i in 1..(y.size - 1)) {
//                sum += abs(y[i] - yDataPercent[i])
//            }
//            if (!percent.equals(0.0) && sum - sumPrev > 0) {
//                return floor((percent - percentStep) * 100.0).toInt()
//            }
//            sumPrev = sum
//            percent += percentStep
//        }
//
//        return 0
//    }

    private fun getMolarMass(dataBaseMock: Line): Double {
        return when (dataBaseMock) {
            is H2O -> molarMass.H2O
            is CO2 -> molarMass.CO2
            is O2 -> molarMass.O2
            else -> throw UnexpectedTypeException()
        }
    }

    private fun getConcentration(dataBaseMock: Line, concentration: ConcentrationMock): Double {
        return when (dataBaseMock) {
            is H2O -> concentration.H2O
            is CO2 -> concentration.CO2
            is O2 -> concentration.O2
            else -> throw UnexpectedTypeException()
        }
    }

    private fun createEmptyAbsorptionSpectrum(minVacuumWavenumber: Double, maxVacuumWavenumber: Double): Array<DoubleArray> {
        var plotVacuumWavenumber: DoubleArray = doubleArrayOf()
        var plotIntensity: DoubleArray = doubleArrayOf()

        val step = 0.1

        var vacuumWavenumber = minVacuumWavenumber

        while (vacuumWavenumber < maxVacuumWavenumber) {
            plotVacuumWavenumber += doubleArrayOf(vacuumWavenumber)
            vacuumWavenumber += step
            plotIntensity += doubleArrayOf(0.0)
        }

        return arrayOf(plotVacuumWavenumber, plotIntensity)
    }

    private fun getAbsorptionSpectrumOfSingleLine(
        xData: DoubleArray,
        yData: DoubleArray,
        vacuumWavenumber: Double,
        S_Tref: Double,
        A: Double,
        yair: Double,
        yself: Double,
        E2shtreha: Double,
        n: Double,
        betta: Double,
        gasPressure: Double,
        gasTemperature: Double,
        gasColumnThickness: Double,
        concentration: Double,
        molarMass: Double
    ): DoubleArray {
        //1)
        //Не могу раccчитать, временно не учитываем
        val Q_Tref: Double = 1.0 //заменить на Q(Tref)
        val Q_T: Double = 1.0 //заменить на Q(T)
        val S_T: Double = S_T(S_Tref = S_Tref, Q_Tref = Q_Tref, Q_T = Q_T, E2shtreha = E2shtreha, gasTemperature = gasTemperature, vacuumWavenumber = vacuumWavenumber)
        // p для верхних слоев атмосферы
        if (gasPressure > 0.15) {
            //2)
            val pself: Double = concentration * gasPressure
            val y_p_T: Double = y_p_T(gasPressure = gasPressure, T = gasTemperature, pself = pself, yair = yair, yself = yself, n = n)
            //3)
            val vacuumWavenumberCorrected = vacuumWavenumberCorrected(vacuumWavenumber = vacuumWavenumber, betta = betta, gasPressure = gasPressure)
            //4)
            var newY: DoubleArray = doubleArrayOf()
            for (i in 0..(xData.size - 1)) {
                newY += doubleArrayOf(S_T * gasColumnThickness * fL(x = xData[i], y_p_T = y_p_T, vacuumWavenumberCorrected = vacuumWavenumberCorrected) * concentration + yData[i])
            }
            return newY
        }
        // p для верхних слоев атмосферы
        else {
            //2)
            val aD_T: Double = aD_T(gasTemperature = gasTemperature, vacuumWavenumber = vacuumWavenumber, molarMass = molarMass)
            //3)
            var newY: DoubleArray = doubleArrayOf()
            for (i in 0..(xData.size - 1)) {
                newY += doubleArrayOf(S_T * gasColumnThickness * fG(x = xData[i], aD_T = aD_T, v = vacuumWavenumber) * concentration + yData[i])
            }
            return newY
        }
    }

    //формула 1
    //Temperature dependence of the line intensity
    private fun S_T(S_Tref: Double, Q_Tref: Double, Q_T: Double, E2shtreha: Double, gasTemperature: Double, vacuumWavenumber: Double) =
        S_Tref * (Q_Tref / Q_T) * (exp(-C2 * E2shtreha / gasTemperature) / exp(-C2 * E2shtreha / T_REF)) * ((1 - exp(-C2 * vacuumWavenumber / gasTemperature)) / (1 - exp(
            -C2 * vacuumWavenumber / T_REF
        )))

    //формула 2
    //Temperature and pressure dependence of the line width
    private fun aD_T(gasTemperature: Double, vacuumWavenumber: Double, molarMass: Double) =
            (vacuumWavenumber / C) * sqrt(2.0 * NA * K * gasTemperature * ln(2.0) / molarMass)

    //формула 3
    //γ(p,T) for a gas at pressure p (atm), temperature T (K) and partial pressure pself (atm)
    private fun y_p_T(gasPressure: Double, T: Double, pself: Double, yair: Double, yself: Double, n: Double) =
            ((T_REF / T).pow(n) * (yair * (gasPressure - pself) + yself * pself))

    //формула 4
    //Pressure shift correction of line position
    private fun vacuumWavenumberCorrected(vacuumWavenumber: Double, betta: Double, gasPressure: Double) =
            vacuumWavenumber + betta * gasPressure

    //формула 5
    //Absorption coefficient
    //Для нижних слоев атмосферы
    private fun fL(x: Double, y_p_T: Double, vacuumWavenumberCorrected: Double) =
            (1 / PI) * (y_p_T / (y_p_T.pow(2.0) + (x - vacuumWavenumberCorrected).pow(2.0)))

    //Для верхних слоев атмосферы
    private fun fG(x: Double, aD_T: Double, v: Double) =
            sqrt(ln(2.0) / (PI * (aD_T.pow(2.0)))) * exp(-((x - v).pow(2.0)) * ln(2.0) / aD_T.pow(2.0))

    private fun getAppropriateRepository(moleculeType: LineType) =
            when (moleculeType) {
                N2 -> n2LineRepository
                CH3CN -> cH3CNLineRepository
                CS -> cSLineRepository
                NO_PLUS -> nOPlusLineRepository
                O -> oLineRepository
                else -> throw UnexpectedTypeException()
            }

    private fun plotAbsorptionSpectrum(vacuumWavenumberData: DoubleArray, intensityData: DoubleArray) {
        val chart = QuickChart.getChart("Absorption Spectrum", "Wave Number", "Intensity", "S(v)", vacuumWavenumberData, intensityData)
        SwingWrapper(chart).displayChart()
        // Save it
        //BitmapEncoder.saveBitmap(chart, "./Sample_Chart", BitmapFormat.PNG)
        // or save it in high-res
        //BitmapEncoder.saveBitmapWithDPI(chart, "./Sample_Chart_300_DPI", BitmapFormat.PNG, 300)
    }

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
