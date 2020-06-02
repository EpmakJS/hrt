package tp.hrt.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import tp.hrt.model.lines.N2Line
import tp.hrt.model.lines.N2LineId

@Repository
interface N2LineRepository : JpaRepository<N2Line, N2LineId> {

    fun findFirstByN2LineIdVacuumWavenumber(vacuumWavenumber: Double): N2Line
}
