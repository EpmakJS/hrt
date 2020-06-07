package tp.hrt.dto

data class DirectTaskDto(
    val vmin: Double,
    val vmax: Double,
    val p: Double,
    val T: Double,
    val l: Double,
    val concentration: Map<Int, Double>
)

interface Line {
    val v: Double
    val S_Tref: Double
    val A: Double
    val yair: Double
    val yself: Double
    val E2shtreha: Double
    val n: Double
    val betta: Double
}

data class O2(
    override val v: Double,
    override val S_Tref: Double,
    override val A: Double,
    override val yair: Double,
    override val yself: Double,
    override val E2shtreha: Double,
    override val n: Double,
    override val betta: Double
) : Line

data class H2O(
    override val v: Double,
    override val S_Tref: Double,
    override val A: Double,
    override val yair: Double,
    override val yself: Double,
    override val E2shtreha: Double,
    override val n: Double,
    override val betta: Double
) : Line

data class N2(
    override val v: Double,
    override val S_Tref: Double,
    override val A: Double,
    override val yair: Double,
    override val yself: Double,
    override val E2shtreha: Double,
    override val n: Double,
    override val betta: Double
) : Line

data class CO2(
    override val v: Double,
    override val S_Tref: Double,
    override val A: Double,
    override val yair: Double,
    override val yself: Double,
    override val E2shtreha: Double,
    override val n: Double,
    override val betta: Double
) : Line

val db1_1: H2O = H2O(
    0.072059,
    2.043e-30,
    5.088e-12,
    0.0919,
    0.391,
    1922.8291,
    0.76,
    0.003700
)

val db1_2: H2O = H2O(
    0.117133,
    2.956e-34,
    1.262e-11,
    0.0894,
    0.371,
    4195.8179,
    0.66,
    0.002000
)

val db1_3: H2O = H2O(
    0.194652,
    9.864e-35,
    1.180e-11,
    0.0793,
    0.412,
    3977.2617,
    0.67,
    0.005300
)

val db1_4: H2O = H2O(
    0.210927,
    1.663e-32,
    4.181e-10,
    0.0826,
    0.391,
    3598.5156,
    0.75,
    0.005300
)

val db1_5: H2O = H2O(
    0.242095,
    9.157e-31,
    1.429e-10,
    0.0750,
    0.298,
    2904.4280,
    0.51,
    0.004900
)

val db1_6: H2O = H2O(
    0.377306,
    8.133e-36,
    2.114e-11,
    0.0613,
    0.352,
    4563.9893,
    0.57,
    -0.000844
)

val db1_7: H2O = H2O(
    0.388373,
    3.117e-34,
    1.722e-10,
    0.0855,
    0.391,
    4149.8989,
    0.67,
    0.006300
)

val db1_8: H2O = H2O(
    0.400572,
    2.352e-28,
    1.009e-09,
    0.0869,
    0.434,
    1907.6158,
    0.65,
    -0.003100
)

val db1_9: H2O = H2O(
    0.416028,
    1.956e-32,
    3.183e-10,
    0.0345,
    0.208,
    3623.7632,
    0.38,
    0.009500
)

val db1_10: H2O = H2O(
    0.511490,
    9.782e-36,
    3.244e-09,
    0.0983,
    0.434,
    5473.1445,
    0.78,
    0.005600
)

val db2_1: CO2 = CO2(
    0.736369,
    6.284e-33,
    3.699e-14,
    0.0927,
    0.125,
    0.0000,
    0.78,
    -0.000029
)

val db2_2: CO2 = CO2(
    1.472734,
    4.000e-32,
    2.840e-13,
    0.0892,
    0.120,
    0.7364,
    0.78,
    -0.000042
)

val db2_3: CO2 = CO2(
    2.209095,
    1.673e-31,
    1.284e-12,
    0.0884,
    0.119,
    2.2091,
    0.78,
    0.000006
)

val db2_4: CO2 = CO2(
    2.945446,
    3.915e-31,
    3.156e-12,
    0.0878,
    0.118,
    4.4182,
    0.77,
    0.000099
)

val db2_5: CO2 = CO2(
    3.681787,
    1.123e-30,
    9.408e-12,
    0.0871,
    0.116,
    7.3636,
    0.76,
    0.000162
)

val db2_6: CO2 = CO2(
    4.418112,
    1.903e-30,
    1.651e-11,
    0.0862,
    0.115,
    11.0454,
    0.75,
    0.000176
)

val db7_1: O2 = O2(
    0.000001,
    3.769e-50,
    9.249e-32,
    0.0346,
    0.034,
    4701.1855,
    0.72,
    0.000000
)

val db7_2: O2 = O2(
    0.000001,
    8.838e-49,
    1.117e-31,
    0.0346,
    0.034,
    4076.1021,
    0.72,
    0.000000
)

val db7_3: O2 = O2(
    0.000001,
    4.008e-48,
    1.246e-31,
    0.0346,
    0.034,
    3779.7904,
    0.72,
    0.000000
)

val db7_4: O2 = O2(
    0.000001,
    1.869e-49,
    1.013e-31,
    0.0346,
    0.034,
    4383.2507,
    0.72,
    0.000000
)

val db7_5: O2 = O2(
    0.000001,
    7.233e-51,
    8.475e-32,
    0.0346,
    0.034,
    5029.8540,
    0.72,
    0.000000
)

val db7_6: O2 = O2(
    0.000002,
    1.125e-42,
    1.208e-30,
    0.0347,
    0.034,
    1422.5024,
    0.72,
    0.000000
)

val db7_7: O2 = O2(
    0.000002,
    3.098e-42,
    1.523e-30,
    0.0348,
    0.034,
    1248.2044,
    0.72,
    0.000000
)

val db7_8: O2 = O2(
    0.000002,
    1.310e-43,
    8.008e-31,
    0.0346,
    0.034,
    1804.8809,
    0.72,
    0.000000
)

val db7_9: O2 = O2(
    0.000002,
    1.046e-45,
    4.124e-31,
    0.0346,
    0.034,
    2703.8583,
    0.72,
    0.000000
)

val db7_10: O2 = O2(
    0.000002,
    3.751e-45,
    4.794e-31,
    0.0346,
    0.034,
    2462.4247,
    0.72,
    0.000000
)

val db22_1: N2 = N2(
    11.833113,
    2.595e-39,
    6.351e-18,
    0.0663,
    0.066,
    2329.9116,
    0.81,
    0.000000
)

val db22_2: N2 = N2(
    11.937345,
    2.286e-34,
    6.813e-18,
    0.0663,
    0.066,
    0.0000,
    0.81,
    0.000000
)

val db22_3: N2 = N2(
    19.721395,
    1.734e-38,
    1.050e-16,
    0.0621,
    0.062,
    2333.8560,
    0.79,
    0.000000
)

val dataBaseMock = arrayOf<Line>(
    db1_1, db1_2, db1_3, db1_4, db1_5, db1_6, db1_7, db1_8, db1_9, db1_10,
    db2_1, db2_2, db2_3, db2_4, db2_5, db2_6,
    db7_1, db7_2, db7_3, db7_4, db7_5, db7_6, db7_7, db7_8, db7_9, db7_10,
    db22_1, db22_2, db22_3
)
