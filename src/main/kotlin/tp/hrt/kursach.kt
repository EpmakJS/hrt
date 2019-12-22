//package tp.hrt
//
//import org.knowm.xchart.*
//import kotlin.math.*
//
////константы
//const val NA: Double = 6.02214129e23
//const val K: Double = 1.3806488e-16 // (erg/K)
//const val H: Double = 6.62606957e-27 // (erg/K)
//const val C: Double = 2.99792458e10 //(cm/s)
//const val C2: Double = 1.4387770 //(cm*K)
//const val T_REF: Double = 296.0 //defolt
//const val P_REF: Double = 1.0 //defolt
//
//
//fun main() {
//    val molarMass = object {
//        val H2O: Double = 18.010565
//        val CO2: Double = 43.989830
//        val O3: Double = 47.984745
//        val N2O: Double = 44.001062
//        val CO: Double = 27.994915
//        val CH4: Double = 16.031300
//        val O2: Double = 31.989830
//        val NO: Double = 29.997989
//        val SO2: Double = 63.961901
//        val NO2: Double = 45.992904
//        val NH3: Double = 17.026549
//        val NHO3: Double = 62.995644
//        val OH: Double = 17.002740
//        val HF: Double = 20.006229
//        val HCL: Double = 35.976678
//        val HBr: Double = 79.926160
//        val HI: Double = 127.912297
//        val ClO: Double = 50.963768
//        val OCS: Double = 59.966986
//        val H2CO: Double = 30.010565
//        val HOCl: Double = 51.971593
//        val N2: Double = 28.006148
//        val HCN: Double = 27.010899
//        val CH3Cl: Double = 49.992328
//        val H2O2: Double = 34.005480
//        val C2H2: Double = 26.015650
//        val C2H6: Double = 30.046950
//        val PH3: Double = 33.997238
//        val COF2: Double = 65.991722
//        val SF6: Double = 145.962492
//        val H2S: Double = 33.987721
//        val HCOOH: Double = 46.005480
//        val HO2: Double = 32.997655
//        val O: Double = 15.994915
//        val ClONO2: Double = 96.956672
//        val NOplus: Double = 29.997989
//        val HOBr: Double = 95.921076
//        val C2H4: Double = 28.031300
//        val CH3OH: Double = 32.026215
//        val CH3Br: Double = 93.941811
//        val CH3CN: Double = 41.026549
//        val CF4: Double = 87.993616
//        val C4H2: Double = 50.015650
//        val HC3N: Double = 51.010899
//        val H2: Double = 2.015650
//        val CS: Double = 43.971036
//        val SO3: Double = 79.956820
//        val C2N2: Double = 52.006148
//        val COCl2: Double = 97.932620
//    }
//
//    val taskParametrs = object {
//        val vmin: Double = 0.0
//        val vmax: Double = 0.5
//        val p: Double = 1.0
//        val T: Double = 296.0
//        val l: Double = 1.0
//        val concentration = object {
//            val O2: Double = 0.26
//            val H2O: Double = 0.02
//            val N2: Double = 0.54
//            val C02: Double = 0.18
//        }
//    }
//    val db = object {
//        val v: Double = 0.117133
//        val S_Tref: Double = 2.956e-34
//        val A: Double = 1.262e-11
//        val yair: Double = 0.0894
//        val yself: Double = 0.371
//        val E2shtreha: Double = 4095.8027
//        val n: Double = 0.66
//        val betta: Double = 0.002000
//    }
//    //прямая задача
//    val xy = createXY(xStep = 0.01, xMin = taskParametrs.vmin, xMax = taskParametrs.vmax)
//    val xData: DoubleArray = xy[0]
//    var yData: DoubleArray = xy[1]
//    yData = createSingleLane(
//            xData = xData, yData = yData, v = db.v, S_Tref = db.S_Tref, A = db.A, yair = db.yair,
//            yself = db.yself, E2shtreha = db.E2shtreha, n = db.n, betta = db.betta, p = taskParametrs.p,
//            T = taskParametrs.T, concentration = taskParametrs.concentration.O2, M = molarMass.O2,
//            l = taskParametrs.l
//    )
//    plot(xData = xData, yData = yData)
//
//
//    //обратная задача
//    var yDataPercent: DoubleArray
//    val percentStep = 0.01
//    var percent: Double = 0.0
//    var sum: Double
//    var sumPrev: Double = 0.0
//    loop@ while (percent < 1.0) {
//        sum = 0.0
//        yDataPercent = xy[1]
//        yDataPercent = createSingleLane(
//                xData = xData, yData = yDataPercent, v = db.v, S_Tref = db.S_Tref, A = db.A, yair = db.yair,
//                yself = db.yself, E2shtreha = db.E2shtreha, n = db.n, betta = db.betta, p = taskParametrs.p,
//                T = taskParametrs.T, concentration = percent, M = molarMass.O2,
//                l = taskParametrs.l
//        )
//        for (i in 1..(yData.size - 1)) {
//            sum += abs(yData[i] - yDataPercent[i])
//        }
//        if (percent !== 0.0 && sum - sumPrev > 0) {
//            println(floor((percent - percentStep) * 100.0).toInt())
//            break@loop
//        }
//        sumPrev = sum
//        percent += percentStep
//    }
//}
//
//fun plot(xData: DoubleArray, yData: DoubleArray) {
//    // Create Chart
//    val chart = QuickChart.getChart("Sample Chart", "X", "Y", "y(x)", xData, yData)
//    // Show it
//    SwingWrapper(chart).displayChart()
//    // Save it
//    //BitmapEncoder.saveBitmap(chart, "./Sample_Chart", BitmapFormat.PNG)
//    // or save it in high-res
//    //BitmapEncoder.saveBitmapWithDPI(chart, "./Sample_Chart_300_DPI", BitmapFormat.PNG, 300)
//}
//
//fun createXY(xStep: Double, xMin: Double, xMax: Double): Array<DoubleArray> {
//    var xNext = xMin
//    var plotX: DoubleArray = doubleArrayOf()
//    var plotY: DoubleArray = doubleArrayOf()
//    while (xNext < xMax) {
//        plotX += doubleArrayOf(xNext)
//        xNext += xStep
//        plotY += doubleArrayOf(0.0)
//    }
//    return arrayOf(plotX, plotY)
//}
//
////fun createNewY(xData: DoubleArray, yData: DoubleArray): DoubleArray{
////    var newY: DoubleArray = doubleArrayOf()
////    for (i in 0..(xData.size-1)){
////        newY = newY + doubleArrayOf(sin(xData[i]) + yData[i])
////    }
////    return newY
////}
//
//fun createSingleLane(xData: DoubleArray, yData: DoubleArray, v: Double,
//                     S_Tref: Double, A: Double, yair: Double, yself: Double,
//                     E2shtreha: Double, n: Double, betta: Double, p: Double,
//                     T: Double, concentration: Double, M: Double, l: Double): DoubleArray {
//    //1)
//    //Не могу рачитать, временно не учитываем
//    val Q_Tref: Double = 1.0 //заменить на Q(Tref)
//    val Q_T: Double = 1.0 //заменить на Q(T)
//    val S_T: Double = S_T(S_Tref = S_Tref, Q_Tref = Q_Tref, Q_T = Q_T, E2shtreha = E2shtreha, T = T, v = v)
//    if (p > 0.15) {
//        //2)
//        val pself: Double = concentration * p
//        val y_p_T: Double = y_p_T(p = p, T = T, pself = pself, yair = yair, yself = yself, n = n)
//        //3)
//        val vCorrected = vCorrected(v = v, betta = betta, p = p)
//        //4)
//        var newY: DoubleArray = doubleArrayOf()
//        for (i in 0..(xData.size - 1)) {
//            newY += doubleArrayOf(S_T * l * fL(x = xData[i], y_p_T = y_p_T, v_corrected = vCorrected) + yData[i])
//        }
//        return newY
//    } else {
//        //2)
//        //M - molar mass (еще нет таблицы)
//        val aD_T: Double = aD_T(T = T, v = v, M = M)
//        //3)
//        var newY: DoubleArray = doubleArrayOf()
//        for (i in 0..(xData.size - 1)) {
//            newY += doubleArrayOf(S_T * l * fG(x = xData[i], aD_T = aD_T, v = v) + yData[i])
//        }
//        return newY
//    }
//}
//
//fun concetrationInGas(yData: DoubleArray, yData100: DoubleArray, percentStep: Double): Double {
//    return 1.0
//}
//
////формула 1
////Temperature dependence of the line intensity
//fun S_T(S_Tref: Double, Q_Tref: Double, Q_T: Double, E2shtreha: Double, T: Double, v: Double) =
//    S_Tref * (Q_Tref / Q_T) * (exp(-C2 * E2shtreha / T) / exp(-C2 * E2shtreha / T_REF)) * ((1 - exp(-C2 * v / T)) / (1 - exp(-C2 * v / T_REF)))
//
////формула 2
////Temperature and pressure dependence of the line width
//fun aD_T(T: Double, v: Double, M: Double) = (v / C) * sqrt(2.0 * NA * K * T * ln(2.0) / M)
//
////формула 3
////γ(p,T) for a gas at pressure p (atm), temperature T (K) and partial pressure pself (atm)
//fun y_p_T(p: Double, T: Double, pself: Double, yair: Double, yself: Double, n: Double) = ((T_REF / T).pow(n) * (yair * (p - pself) + yself * pself))
//
////формула 4
////Pressure shift correction of line position
//fun vCorrected(v: Double, betta: Double, p: Double) = v + betta * p
//
////формула 5
////Absorption coefficient
////Для нижних слоев атмосферы
//fun fL(x: Double, y_p_T: Double, v_corrected: Double) = (1 / PI) * (y_p_T / (y_p_T.pow(2.0) + (x - v_corrected).pow(2.0)))
//
////Для верхних слоев атмосферы
//fun fG(x: Double, aD_T: Double, v: Double) =
//    sqrt(ln(2.0) / (PI * (aD_T.pow(2.0)))) * exp(-((x - v).pow(2.0)) * ln(2.0) / aD_T.pow(2.0))
//
//
////формула 6
////The monochromatic absorption coefficient kij(ν,T,p)
////fun k(z: Double) = S_T*fL()
//
//
////формула 7
////The dimensionless optical depth
////val X = 1.0
////var u = l*X
////fun tay(z: Double) = u*k(z)
