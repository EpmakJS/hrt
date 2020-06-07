package tp.hrt.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.NoRepositoryBean
import tp.hrt.model.lines.BaseLine
import java.io.Serializable

@NoRepositoryBean
interface BaseLineRepository<T : BaseLine, E: Serializable> : JpaRepository<T, E> {

    fun findInRangeByVacuumWavenumber(min: Double, max: Double): List<T>?
}
