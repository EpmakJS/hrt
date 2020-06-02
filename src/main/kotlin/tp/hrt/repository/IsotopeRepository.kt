package tp.hrt.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import tp.hrt.model.Isotope

@Repository
interface IsotopeRepository : JpaRepository<Isotope, Int> {

    fun findIsotopeById(id: Int): Isotope
}
