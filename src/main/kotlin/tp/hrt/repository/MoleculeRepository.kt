package tp.hrt.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import tp.hrt.model.Molecule

@Repository
interface MoleculeRepository : JpaRepository<Molecule, Int> {

    fun findMoleculeById(id: Int): Molecule
}
