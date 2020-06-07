package tp.hrt.service

import tp.hrt.dto.DirectTaskDto

interface HitranService {

    fun getAbsorptionSpectrumOfGasMixture(directTaskDto: DirectTaskDto)

//    fun findConcentration(x: DoubleArray, y: DoubleArray, emptyY: DoubleArray, directTaskDto: DirectTaskDto): Int
}
