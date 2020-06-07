package tp.hrt.repository

import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import tp.hrt.model.lines.N2Line
import tp.hrt.model.lines.N2LineId

@Repository
interface N2LineRepository : BaseLineRepository<N2Line, N2LineId> {

    @Query("SELECT line FROM N2Line line " +
            "WHERE line.lineId.vacuumWavenumber = :vacuumWavenumber")
    fun findFirstByN2LineIdVacuumWavenumber(vacuumWavenumber: Double): N2Line

    @Query("SELECT nl FROM N2Line nl " +
            "WHERE nl.lineId.vacuumWavenumber BETWEEN :min AND :max")
    override fun findInRangeByVacuumWavenumber(min: Double, max: Double): List<N2Line>?
}
