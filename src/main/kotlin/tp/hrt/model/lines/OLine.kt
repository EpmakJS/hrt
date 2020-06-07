package tp.hrt.model.lines

import com.fasterxml.jackson.annotation.JsonIgnore
import tp.hrt.model.Molecule
import tp.hrt.model.lines.LineType.O
import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "o_lines")
class OLine : BaseLine() {

    @EmbeddedId
    override lateinit var lineId: OLineId

    @Column(name = "lower_state_energy", nullable = false)
    override val lowerStateEnergy: Double = 0.0

    @Column(name = "temperature_dependence", nullable = false)
    override val temperatureDependence: Double = 0.0

    @Column(name = "air_pressure", nullable = false)
    override val airPressure: Double = 0.0

    @Column(name = "upper_state_global_quanta", nullable = false)
    override val upperStateGlobalQuanta: String = ""

    @Column(name = "lower_state_global_quanta", nullable = false)
    override val lowerStateGlobalQuanta: String = ""

    @Column(name = "i_err", nullable = false)
    override val uncertaintyIndices: Long = 0L

    @Column(name = "i_ref", nullable = false)
    override val referenceIndices: Long = 0L

    @Column(name = "flag", nullable = false)
    override val flag: String = ""

    @Column(name = "g_upper", nullable = false)
    override val upperStateStatisticalWeight: Double = 0.0

    @Column(name = "g_lower", nullable = false)
    override val lowerStateStatisticalWeight: Double = 0.0

    @ManyToOne
    @JoinColumn(name = "mol_id")
    @JsonIgnore
    lateinit var molecule: Molecule

    @Transient
    override val lineType: LineType = O
}

@Embeddable
class OLineId(
    @Column(name = "iso_number", nullable = false) override val isoNumber: Long,
    @Column(name = "vacuum_wavenumber", nullable = false) override val vacuumWavenumber: Double,
    @Column(name = "intensity", nullable = false) override val intensity: Double,
    @Column(name = "einstein_a", nullable = false) override val einsteinA: Double,
    @Column(name = "gamma_air", nullable = false) override val gammaAir: Double,
    @Column(name = "gamma_self", nullable = false) override val gammaSelf: Double,
    @Column(name = "upper_state_local_quanta", nullable = false) override val upperStateLocalQuanta: String,
    @Column(name = "lower_state_local_quanta", nullable = false) override val lowerStateLocalQuanta: String
) : BaseLineId() {

    companion object {
        private const val serialVersionUID = -19692L
    }
}
