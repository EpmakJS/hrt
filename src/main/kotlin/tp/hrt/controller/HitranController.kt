package tp.hrt.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tp.hrt.dto.DirectTaskDto
import tp.hrt.repository.N2LineRepository
import tp.hrt.service.HitranService
import tp.hrt.service.MoleculeService

@RestController
@RequestMapping("/hitran")
class HitranController(
        private val hitranService: HitranService,
        private val moleculeService: MoleculeService,
        private val n2LineRepository: N2LineRepository
) {

    @PostMapping("/direct-task")
    fun drawPlot(@RequestBody directTaskDto: DirectTaskDto) = hitranService.drawPlot(directTaskDto)

    @GetMapping("molecule")
    fun getAllMolecules() = moleculeService.findAll()

    @GetMapping("molecule/n2/line/by-vacuum-wavenumber/{vacuumWavenumber}")
    fun getN2LineByVacuumWavenumber(@PathVariable vacuumWavenumber: Double) =
        n2LineRepository.findFirstByN2LineIdVacuumWavenumber(vacuumWavenumber)

    @GetMapping("molecule/{id}")
    fun getMoleculeById(@PathVariable id: Int) = moleculeService.findMoleculeById(id)

//    @PostMapping("/inverse-task")
//    fun createClient(@RequestBody inverseTaskDto: InverseTaskDto) = hitranService.findConcentration(inverseTaskDto)
}
