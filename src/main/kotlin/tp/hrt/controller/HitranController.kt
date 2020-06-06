package tp.hrt.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
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
    fun getAbsorptionSpectrumOfGasMixture(@RequestBody directTaskDto: DirectTaskDto) = hitranService.getAbsorptionSpectrumOfGasMixture(directTaskDto)

    @GetMapping("/molecule")
    fun getAllMolecules() = moleculeService.findAll()

    @GetMapping("/line/n2/by-vacuum-wavenumber/{vacuumWavenumber}")
    fun getN2LineByVacuumWavenumber(@PathVariable vacuumWavenumber: Double) =
        n2LineRepository.findFirstByN2LineIdVacuumWavenumber(vacuumWavenumber)

    @GetMapping("/molecule/{id}")
    fun getMoleculeById(@PathVariable id: Int) = moleculeService.findMoleculeById(id)

    @GetMapping("/line/n2/in-range-by-vacuum-wavenumber")
    fun getN2LinesInRangeByVacuumWavenumber(
        @RequestParam minVacuumWavenumber: Double,
        @RequestParam maxVacuumWavenumber: Double
    ) = n2LineRepository.findN2LinesByN2LineIdVacuumWavenumberBetween(minVacuumWavenumber, maxVacuumWavenumber)

//    @PostMapping("/inverse-task")
//    fun createClient(@RequestBody inverseTaskDto: InverseTaskDto) = hitranService.findConcentration(inverseTaskDto)
}
