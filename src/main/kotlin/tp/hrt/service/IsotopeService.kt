package tp.hrt.service

import tp.hrt.model.Isotope

interface IsotopeService {

    fun findIsotopeById(id: Int): Isotope
}
