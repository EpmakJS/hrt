package tp.hrt.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import tp.hrt.model.lines.CH3CNLine
import tp.hrt.model.lines.CH3CNLineId

@Repository
interface CH3CNLineRepository : JpaRepository<CH3CNLine, CH3CNLineId> {

    fun findFirstByCh3cnLineIdVacuumWavenumber(vacuumWavenumber: Double): CH3CNLine
}
