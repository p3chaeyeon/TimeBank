package kookmin.software.capstone2023.timebank.presentation.api.v1

import kookmin.software.capstone2023.timebank.application.exception.NotFoundException
import kookmin.software.capstone2023.timebank.application.service.bank.account.BankAccountCreateService
import kookmin.software.capstone2023.timebank.application.service.bank.account.BankAccountReadService
import kookmin.software.capstone2023.timebank.presentation.api.RequestAttributes
import kookmin.software.capstone2023.timebank.presentation.api.auth.model.UserAuthentication
import kookmin.software.capstone2023.timebank.presentation.api.auth.model.UserContext
import kookmin.software.capstone2023.timebank.presentation.api.v1.model.bank.account.BankAccountCreateRequestData
import kookmin.software.capstone2023.timebank.presentation.api.v1.model.bank.account.BankAccountCreateResponseData
import kookmin.software.capstone2023.timebank.presentation.api.v1.model.bank.account.BankAccountReadRequestData
import kookmin.software.capstone2023.timebank.presentation.api.v1.model.bank.account.BankAccountReadResponseData
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@UserAuthentication
@RestController
@RequestMapping("api/v1/bank/account")
class BankAccountController(
    private val bankAccountCreateService: BankAccountCreateService,
    private val bankAccountReadService: BankAccountReadService,
) {

    // 은행 계좌 생성 API
    @PostMapping
    fun createBankAccount(
        @RequestAttribute(RequestAttributes.USER_CONTEXT) userContext: UserContext,
        @Validated @RequestBody
        data: BankAccountCreateRequestData,
    ): BankAccountCreateResponseData {
        val createdBankAccount: BankAccountCreateService.CreatedBankAccount =
            bankAccountCreateService.createBankAccount(
                userId = userContext.userId,
                accountId = userContext.accountId,
                branchId = data.branchId,
                password = data.password,
            )

        return BankAccountCreateResponseData(
            balance = createdBankAccount.balance,
            accountNumber = createdBankAccount.accountNumber, // 은행 계좌 번호
            bankAccountId = createdBankAccount.bankAccountId, // 은행 계좌 id
        )
    }

    // 은행 계좌 조회 API
    @GetMapping
    fun readBankAccount(
        @RequestAttribute(RequestAttributes.USER_CONTEXT) userContext: UserContext,
        @Validated @RequestBody
        data: BankAccountReadRequestData,
    ): BankAccountReadResponseData {
        val readedBankAccount: BankAccountReadService.ReadedBankAccount =
            bankAccountReadService.readBankAccountByBankAccountNumber(
                userId = userContext.userId,
                accountId = userContext.accountId,
                bankAccountNumber = data.bankAccountNumber,
            )

        return BankAccountReadResponseData(
            balance = readedBankAccount.balance,
            accountNumber = readedBankAccount.accountNumber,
            bankAccountId = readedBankAccount.bankAccountId,
        )
    }

    // 은행 계좌 존재 여부 확인 API (단순 조회)
    @GetMapping("/{accountNumber}")
    fun checkBankAccount(
        @PathVariable("accountNumber")
        accountNumber: String,
    ): BankAccountReadResponseData {
        if (!bankAccountReadService.isAccountNumberExists(accountNumber)) {
            throw NotFoundException(message = "찾으시는 계좌가 존재하지 않습니다.")
        }
        return BankAccountReadResponseData(
            accountNumber = accountNumber,
        )
    }
}
