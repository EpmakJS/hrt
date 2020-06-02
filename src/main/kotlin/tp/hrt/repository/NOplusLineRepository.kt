package tp.hrt.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import tp.hrt.model.lines.NOplusLine
import tp.hrt.model.lines.NOplusLineId

@Repository
interface NOplusLineRepository : JpaRepository<NOplusLine, NOplusLineId> {

    fun findFirstByNOplusLineIdVacuumWavenumber(vacuumWavenumber: Double): NOplusLine
}
