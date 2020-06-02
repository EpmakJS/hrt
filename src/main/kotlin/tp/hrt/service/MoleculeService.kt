package tp.hrt.service

import tp.hrt.model.Molecule

interface MoleculeService {

    fun findAll(): List<Molecule>

    fun findMoleculeById(id: Int): Molecule
}
