package tp.hrt.model

import com.fasterxml.jackson.annotation.JsonIgnore
import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Entity
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

    @Column(name = "desc")
    val description: String = ""

    @OneToMany(mappedBy = "molecule")
    @JsonIgnore
    val isotope: List<Isotope> = emptyList()

    @OneToMany(mappedBy = "molecule")
    @JsonIgnore
    val lines: List<Isotope> = emptyList()
}
