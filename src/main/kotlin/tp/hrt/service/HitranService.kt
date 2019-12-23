package tp.hrt.service

import tp.hrt.dto.DirectTaskDto
import tp.hrt.dto.InverseTaskDto

interface HitranService {

    fun drawPlot(directTaskDto: DirectTaskDto): Int

    fun findConcentration(x: DoubleArray, y: DoubleArray, emptyY: DoubleArray, directTaskDto: DirectTaskDto): Int
}