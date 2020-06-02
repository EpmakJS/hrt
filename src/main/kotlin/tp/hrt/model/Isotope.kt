package tp.hrt.model

import com.fasterxml.jackson.annotation.JsonIgnore
import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "isotope")
class Isotope : Serializable {

    @Id
    val id: Int = 0

    @Column
    val isoId: Int = 0

    @Column
    val isoNumber: Int = 0

    @ManyToOne
    @JoinColumn(name = "mol_id")
    @JsonIgnore
    lateinit var molecule: Molecule

    @Column(name = "desc")
    val description: String = ""
}
