package tp.hrt.model.lines

import com.fasterxml.jackson.annotation.JsonIgnore
import tp.hrt.model.Molecule
import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "noplus_lines")
class NOPlusLine : Serializable {

    @EmbeddedId
    lateinit var noPlusLineId: NOPlusLineId

    @Column(name = "lower_state_energy", nullable = false)
    val lowerStateEnergy: Double = 0.0

    @Column(name = "temperature_dependence", nullable = false)
    val temperatureDependence: Double = 0.0

    @Column(name = "air_pressure", nullable = false)
    val airPressure: Double = 0.0

    @Column(name = "upper_state_global_quanta", nullable = false)
    val upperStateGlobalQuanta: String = ""

    @Column(name = "lower_state_global_quanta", nullable = false)
    val lowerStateGlobalQuanta: String = ""

    @Column(name = "i_err", nullable = false)
    val uncertaintyIndices: Long = 0L

    @Column(name = "i_ref", nullable = false)
    val referenceIndices: Long = 0L

    @Column(name = "flag", nullable = false)
    val flag: String = ""

    @Column(name = "g_upper", nullable = false)
    val upperStateStatisticalWeight: Double = 0.0

    @Column(name = "g_lower", nullable = false)
    val lowerStateStatisticalWeight: Double = 0.0

    @ManyToOne
    @JoinColumn(name = "mol_id")
    @JsonIgnore
    lateinit var molecule: Molecule
}

@Embeddable
class NOPlusLineId(
    @Column(name = "iso_number", nullable = false) val isoNumber: Long,
    @Column(name = "vacuum_wavenumber", nullable = false) val vacuumWavenumber: Double,
    @Column(name = "intensity", nullable = false) val intensity: Double,
    @Column(name = "einstein_a", nullable = false) val einsteinA: Double,
    @Column(name = "gamma_air", nullable = false) val gammaAir: Double,
    @Column(name = "gamma_self", nullable = false) val gammaSelf: Double,
    @Column(name = "upper_state_local_quanta", nullable = false) val upperStateLocalQuanta: String,
    @Column(name = "lower_state_local_quanta", nullable = false) val lowerStateLocalQuanta: String
) : Serializable {

    companion object {
        private const val serialVersionUID = -19691L
    }
}
