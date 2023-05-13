package kookmin.software.capstone2023.timebank.application.service.user

import kookmin.software.capstone2023.timebank.domain.model.AccountType
import kookmin.software.capstone2023.timebank.domain.repository.AccountJpaRepository
import kookmin.software.capstone2023.timebank.domain.repository.UserJpaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserWithdrawalService(
    private val userJpaRepository: UserJpaRepository,
    private val accountJpaRepository: AccountJpaRepository,
) {
    @Transactional
    fun withdrawal(userId: Long) {
        val user = userJpaRepository.getUserById(userId)
            ?: throw IllegalArgumentException("User not found")

        val account = accountJpaRepository.getAccountById(user.accountId)
            ?: throw IllegalArgumentException("Account not found")

        userJpaRepository.delete(user)

        // 개인 계정이면 계정도 삭제
        if (account.type == AccountType.INDIVIDUAL) {
            accountJpaRepository.delete(account)
        }
    }
}