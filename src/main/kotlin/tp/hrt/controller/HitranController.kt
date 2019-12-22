package tp.hrt.controller

import org.springframework.web.bind.annotation.*
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
//    fun createClient(@RequestBody clientDto: ClientDto): ClientDto = clientService.createClient(clientDto)
}