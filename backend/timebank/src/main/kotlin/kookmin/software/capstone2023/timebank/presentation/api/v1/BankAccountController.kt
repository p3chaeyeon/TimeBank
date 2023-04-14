package kookmin.software.capstone2023.timebank.presentation.api.v1
import kookmin.software.capstone2023.timebank.application.service.bank.account.BankAccountCreateService
import kookmin.software.capstone2023.timebank.application.service.bank.account.BankAccountReadService
import kookmin.software.capstone2023.timebank.presentation.api.RequestAttributes
import kookmin.software.capstone2023.timebank.presentation.api.auth.model.UserAuthentication
import kookmin.software.capstone2023.timebank.presentation.api.auth.model.UserContext
import kookmin.software.capstone2023.timebank.presentation.api.v1.model.BankAccountCreateRequestData
import kookmin.software.capstone2023.timebank.presentation.api.v1.model.BankAccountCreateResponseData
import kookmin.software.capstone2023.timebank.presentation.api.v1.model.BankAccountReadRequestData
import kookmin.software.capstone2023.timebank.presentation.api.v1.model.BankAccountReadResponseData
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
    ): BankAccountCreateResponseData {

        val createdBankAccount: BankAccountCreateService.CreatedBankAccount = bankAccountCreateService.createBankAccount(
            userId = userContext.userId,
            accountId = userContext.accountId,
            branchId = data.branchId,
            encryptedPassword = data.password,
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
    ): BankAccountReadResponseData{

        val readedBankAccount: BankAccountReadService.ReadedBankAccount = bankAccountReadService.readBankAccount(
            userId = userContext.userId,
            accountId = userContext.accountId,
            bankAccountId = data.bankAccountId
        )

        return BankAccountReadResponseData(
            balance = readedBankAccount.balance,
            accountNumber = readedBankAccount.accountNumber,
            bankAccountId = readedBankAccount.bankAccountId
        )
    }

    @GetMapping("/bank-account-number")
    fun readBankAccountByBankAccountNumber(
        @RequestAttribute(RequestAttributes.USER_CONTEXT) userContext: UserContext,
        @Validated @RequestBody data: BankAccountReadRequestData
    ): BankAccountReadResponseData{

        val readedBankAccount: BankAccountReadService.ReadedBankAccount = bankAccountReadService.readBankAccountByBankAccountNumber(
            userId = userContext.userId,
            accountId = userContext.accountId,
            bankAccountNumber = data.bankAccountNumber
        )

        return BankAccountReadResponseData(
            balance = readedBankAccount.balance,
            accountNumber = readedBankAccount.accountNumber,
            bankAccountId = readedBankAccount.bankAccountId
        )
    }
}