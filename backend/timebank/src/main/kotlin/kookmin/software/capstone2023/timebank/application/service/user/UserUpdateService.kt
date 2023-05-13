package kookmin.software.capstone2023.timebank.application.service.user

import kookmin.software.capstone2023.timebank.application.exception.NotFoundException
import kookmin.software.capstone2023.timebank.domain.model.Account
import kookmin.software.capstone2023.timebank.domain.model.AccountType
import kookmin.software.capstone2023.timebank.domain.model.User
import kookmin.software.capstone2023.timebank.domain.repository.AccountJpaRepository
import kookmin.software.capstone2023.timebank.domain.repository.UserJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserUpdateService(
    private val userJpaRepository: UserJpaRepository,
    private val accountJpaRepository: AccountJpaRepository,
) {
    @Transactional
    fun updateUserInfo(
        userId: Long,
        name: String,
        phoneNumber: String,
    ) {
        val user: User = userJpaRepository.findByIdOrNull(userId)
            ?: throw NotFoundException(message = "사용자를 찾을 수 없습니다.")

        val account: Account = accountJpaRepository.findByIdOrNull(user.accountId)
            ?: throw NotFoundException(message = "계정을 찾을 수 없습니다.")

        user.updateUserInfo(
            name = name,
            phoneNumber = phoneNumber,
        )

        // 개인 계정의 경우 계정 이름 변경
        if (account.type == AccountType.INDIVIDUAL) {
            account.updateName(
                name = name,
            )
        }
    }
}