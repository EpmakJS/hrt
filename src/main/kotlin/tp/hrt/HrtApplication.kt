package tp.hrt

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder


@SpringBootApplication
class HrtApplication

fun main(args: Array<String>) {
	val builder = SpringApplicationBuilder(HrtApplication::class.java)
	builder.headless(false).run(*args)
}

