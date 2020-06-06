package tp.hrt.model

import com.fasterxml.jackson.annotation.JsonIgnore
import tp.hrt.model.lines.LineType
import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType.STRING
import javax.persistence.Enumerated
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "molecule")
class Molecule : Serializable {

    @Id
    @Column(name = "mol_id")
    val id: Int = 0

    @Column(name = "molar_mass")
    val molarMass: Double = 0.0

    @Column(name = "type")
    @Enumerated(STRING)
    lateinit var type: LineType

    @OneToMany(mappedBy = "molecule")
    @JsonIgnore
    val isotope: List<Isotope> = emptyList()
}
