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

    abstract val lineType: LineType
}
