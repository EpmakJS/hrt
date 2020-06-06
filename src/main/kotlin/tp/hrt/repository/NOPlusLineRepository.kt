package tp.hrt.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import tp.hrt.model.lines.NOPlusLine
import tp.hrt.model.lines.NOPlusLineId

@Repository
interface NOPlusLineRepository : JpaRepository<NOPlusLine, NOPlusLineId> {

    fun findFirstByNoPlusLineIdVacuumWavenumber(vacuumWavenumber: Double): NOPlusLine?
}
