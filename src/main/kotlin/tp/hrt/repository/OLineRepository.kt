package tp.hrt.repository

import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import tp.hrt.model.lines.OLine
import tp.hrt.model.lines.OLineId

@Repository
interface OLineRepository : BaseLineRepository<OLine, OLineId> {

    @Query("SELECT line FROM OLine line " +
            "WHERE line.lineId.vacuumWavenumber BETWEEN :min AND :max")
    override fun findInRangeByVacuumWavenumber(min: Double, max: Double): List<OLine>?
}
