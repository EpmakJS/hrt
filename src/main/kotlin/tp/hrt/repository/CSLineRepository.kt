package tp.hrt.repository

import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import tp.hrt.model.lines.CSLine
import tp.hrt.model.lines.CSLineId

@Repository
interface CSLineRepository : BaseLineRepository<CSLine, CSLineId> {

    @Query("SELECT line FROM CSLine line " +
            "WHERE line.lineId.vacuumWavenumber BETWEEN :min AND :max")
    override fun findInRangeByVacuumWavenumber(min: Double, max: Double): List<CSLine>?
}
