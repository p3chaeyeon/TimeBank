package kookmin.software.capstone2023.timebank.application.service.user

import kookmin.software.capstone2023.timebank.domain.model.User
import kookmin.software.capstone2023.timebank.domain.repository.UserJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserFinder(
    private val userJpaRepository: UserJpaRepository,
) {
    fun findById(userId: Long): User? {
        return userJpaRepository.findByIdOrNull(userId)
    }
}
