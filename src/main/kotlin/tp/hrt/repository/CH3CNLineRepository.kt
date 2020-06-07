package tp.hrt.repository

import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import tp.hrt.model.lines.CH3CNLine
import tp.hrt.model.lines.CH3CNLineId

@Repository
interface CH3CNLineRepository : BaseLineRepository<CH3CNLine, CH3CNLineId> {

    @Query("SELECT line FROM CH3CNLine line " +
            "WHERE line.lineId.vacuumWavenumber BETWEEN :min AND :max")
    override fun findInRangeByVacuumWavenumber(min: Double, max: Double): List<CH3CNLine>?
}
