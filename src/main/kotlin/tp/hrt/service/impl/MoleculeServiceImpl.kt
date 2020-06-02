package tp.hrt.service.impl

import org.springframework.stereotype.Service
import tp.hrt.model.Molecule
import tp.hrt.repository.MoleculeRepository
import tp.hrt.service.MoleculeService

@Service
class MoleculeServiceImpl(
    private val moleculeRepository: MoleculeRepository
) : MoleculeService {

    override fun findAll(): List<Molecule> = moleculeRepository.findAll()

    override fun findMoleculeById(id: Int) = moleculeRepository.findMoleculeById(id)
}
