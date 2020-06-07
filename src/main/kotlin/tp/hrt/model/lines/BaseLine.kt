package tp.hrt.model.lines

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonSubTypes.Type
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME
import java.io.Serializable
import javax.persistence.MappedSuperclass

@MappedSuperclass
@JsonTypeInfo(use = NAME, include = PROPERTY, property = "lineType")
@JsonSubTypes(
    Type(value = N2Line::class, name = "N2"),
    Type(value = OLine::class, name = "O"),
    Type(value = NOPlusLine::class, name = "NO_PLUS"),
    Type(value = CH3CNLine::class, name = "CH3CN"),
    Type(value = CSLine::class, name = "CS")
)
abstract class BaseLine : Serializable {

    abstract val lineId: BaseLineId
    abstract val lowerStateEnergy: Double
    abstract val temperatureDependence: Double
    abstract val airPressure: Double
    abstract val upperStateGlobalQuanta: String
    abstract val lowerStateGlobalQuanta: String
    abstract val uncertaintyIndices: Long
    abstract val referenceIndices: Long
    abstract val flag: String
    abstract val upperStateStatisticalWeight: Double
    abstract val lowerStateStatisticalWeight: Double

    abstract val lineType: LineType
}

abstract class BaseLineId : Serializable {

    abstract val isoNumber: Long
    abstract val vacuumWavenumber: Double
    abstract val intensity: Double
    abstract val einsteinA: Double
    abstract val gammaAir: Double
    abstract val gammaSelf: Double
    abstract val upperStateLocalQuanta: String
    abstract val lowerStateLocalQuanta: String
}
