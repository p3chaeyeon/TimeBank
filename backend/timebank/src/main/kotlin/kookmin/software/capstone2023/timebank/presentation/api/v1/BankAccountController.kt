package kookmin.software.capstone2023.timebank.presentation.controller

import kookmin.software.capstone2023.timebank.application.service.bank.account.BankAccountCreateService
import kookmin.software.capstone2023.timebank.application.service.bank.account.BankAccountReadService
import kookmin.software.capstone2023.timebank.presentation.api.RequestAttributes
import kookmin.software.capstone2023.timebank.presentation.api.auth.model.UserAuthentication
import kookmin.software.capstone2023.timebank.presentation.api.auth.model.UserContext
import kookmin.software.capstone2023.timebank.presentation.api.v1.bank.model.BankAccountCreateRequestData
import kookmin.software.capstone2023.timebank.presentation.api.v1.bank.model.BankAccountCreateResponseData
import kookmin.software.capstone2023.timebank.presentation.api.v1.model.BankAccountReadRequestData
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@UserAuthentication
@RestController
@RequestMapping("api/v1/bank/account")
class BankAccountController(
    private val bankAccountCreateService: BankAccountCreateService,
    private val bankAccountReadService: BankAccountReadService
) {
    @PostMapping
    fun createBankAccount(
        @RequestAttribute(RequestAttributes.USER_CONTEXT) userContext: UserContext,
        @Validated @RequestBody data: BankAccountCreateRequestData
    ):BankAccountCreateResponseData {

        val createdBankAccount: BankAccountCreateService.CreatedBankAccount = bankAccountCreateService.createBankAccount(
            userId = userContext.userId,
            accountId = userContext.accountId,
            branchId = data.branchId,
            encryptedPin = data.password,
            iv = data.iv,
        )

        return BankAccountCreateResponseData(
            balance = createdBankAccount.balance,
            accountNumber = createdBankAccount.accountNumber,
            bankAccountId = createdBankAccount.bankAccountId
        )
    }

    // 은행 계좌 조회
    @GetMapping
    fun readBankAccount(
        @RequestAttribute(RequestAttributes.USER_CONTEXT) userContext: UserContext,
        @Validated @RequestBody data: BankAccountReadRequestData
    ): BankAccountReadService.ReadedBankAccount{
        // 은행 계좌 조회 서비스를 이용해 은행 계좌 조회
        return bankAccountReadService.readBankAccount(
            userId = userContext.userId,
            accountId = userContext.accountId,
            bankAccountId = data.bankAccountId
        )
    }

}