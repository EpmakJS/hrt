package tp.hrt.repository

import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import tp.hrt.model.lines.NOPlusLine
import tp.hrt.model.lines.NOPlusLineId

@Repository
interface NOPlusLineRepository : BaseLineRepository<NOPlusLine, NOPlusLineId> {

    @Query("SELECT line FROM NOPlusLine line " +
            "WHERE line.lineId.vacuumWavenumber BETWEEN :min AND :max")
    override fun findInRangeByVacuumWavenumber(min: Double, max: Double): List<NOPlusLine>?
}
