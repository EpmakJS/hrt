package tp.hrt.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import tp.hrt.model.lines.OLine
import tp.hrt.model.lines.OLineId

@Repository
interface OLineRepository : JpaRepository<OLine, OLineId> {

    fun findFirstByOLineIdVacuumWavenumber(vacuumWavenumber: Double): OLine?
}
