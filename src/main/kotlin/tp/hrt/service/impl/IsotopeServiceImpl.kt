package tp.hrt.service.impl

import org.springframework.stereotype.Service
import tp.hrt.repository.IsotopeRepository
import tp.hrt.service.IsotopeService

@Service
class IsotopeServiceImpl(
    private val isotopeRepository: IsotopeRepository
) : IsotopeService {

    override fun findIsotopeById(id: Int) = isotopeRepository.findIsotopeById(id)
}
