package tp.hrt.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import tp.hrt.model.lines.N2Line
import tp.hrt.model.lines.N2LineId

@Repository
interface N2LineRepository : JpaRepository<N2Line, N2LineId> {

    @Query("SELECT nl FROM N2Line nl " +
            "WHERE nl.n2LineId.vacuumWavenumber = :vacuumWavenumber")
    fun findFirstByN2LineIdVacuumWavenumber(vacuumWavenumber: Double): N2Line?

    @Query("SELECT nl FROM N2Line nl " +
            "WHERE nl.n2LineId.vacuumWavenumber BETWEEN :min AND :max")
    fun findN2LinesByN2LineIdVacuumWavenumberBetween(min: Double, max: Double): List<N2Line>?
}
