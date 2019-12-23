package tp.hrt.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tp.hrt.dto.DirectTaskDto
import tp.hrt.service.HitranService


@RestController
@RequestMapping("/hitran")
class HitranController(
        private val hitranService: HitranService
) {

    @PostMapping("/direct-task")
    fun drawPlot(@RequestBody directTaskDto: DirectTaskDto) = hitranService.drawPlot(directTaskDto)

//    @PostMapping("/inverse-task")
//    fun createClient(@RequestBody inverseTaskDto: InverseTaskDto) = hitranService.findConcentration(inverseTaskDto)
}