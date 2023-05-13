package kookmin.software.capstone2023.timebank.application.service.account

import kookmin.software.capstone2023.timebank.domain.model.Account
import kookmin.software.capstone2023.timebank.domain.repository.AccountJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class AccountFinder(
    private val accountJpaRepository: AccountJpaRepository,
) {
    fun findById(accountId: Long): Account? {
        return accountJpaRepository.findByIdOrNull(accountId)
    }
}