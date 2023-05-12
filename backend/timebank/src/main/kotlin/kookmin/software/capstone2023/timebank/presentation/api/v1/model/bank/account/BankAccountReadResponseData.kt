package kookmin.software.capstone2023.timebank.presentation.api.v1.model.bank.account

import kookmin.software.capstone2023.timebank.domain.model.OwnerType
import java.math.BigDecimal
import java.time.LocalDateTime

data class BankAccountReadResponseData(
    var bankAccountId: Long? = null, // 은행 계좌 id
    var branchId: Long? = null, // 지점 id
    var balance: BigDecimal? = null, // 잔액
    var createdAt: LocalDateTime? = null, // 개설 일자
    val bankAccountNumber: String, // 계좌 번호
    val ownerName: String, // 소유주명
    val ownerType: OwnerType, // 소유주 타입
)
