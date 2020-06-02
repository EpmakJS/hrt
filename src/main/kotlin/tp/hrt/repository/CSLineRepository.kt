package tp.hrt.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import tp.hrt.model.lines.CSLine
import tp.hrt.model.lines.CSLineId

@Repository
interface CSLineRepository : JpaRepository<CSLine, CSLineId> {

    fun findFirstByCsLineIdVacuumWavenumber(vacuumWavenumber: Double): CSLine
}
