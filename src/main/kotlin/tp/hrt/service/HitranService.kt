package tp.hrt.service

import tp.hrt.dto.DirectTaskDto

interface HitranService {

    fun drawPlot(directTaskDto: DirectTaskDto)
}