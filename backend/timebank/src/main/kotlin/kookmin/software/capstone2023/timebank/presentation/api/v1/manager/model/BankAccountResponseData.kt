package kookmin.software.capstone2023.timebank.presentation.api.v1.manager.model

import kookmin.software.capstone2023.timebank.domain.model.BankAccount
import java.math.BigDecimal
import java.time.ZoneId
import java.time.ZonedDateTime

data class BankAccountResponseData(
    val id: Long,
    val branchId: Long,
    val branchName: String,
    val accountId: Long,
    val accountName: String,
    val accountNumber: String,
    val balanceAmount: BigDecimal,
    val createdAt: ZonedDateTime,
) {
    companion object {
        fun fromDomain(bankAccount: BankAccount): BankAccountResponseData {
            return BankAccountResponseData(
                id = bankAccount.id,
                branchId = bankAccount.branch.id,
                branchName = bankAccount.branch.name,
                accountId = bankAccount.account.id,
                accountName = bankAccount.account.name,
                accountNumber = bankAccount.accountNumber,
                balanceAmount = bankAccount.balance,
                createdAt = bankAccount.createdAt.atZone(ZoneId.of("Asia/Seoul")),
            )
        }
    }
}
