package kookmin.software.capstone2023.timebank.presentation.api.v1

import kookmin.software.capstone2023.timebank.application.service.bank.account.BankAccountCreateService
import kookmin.software.capstone2023.timebank.application.service.bank.account.BankAccountReadService
import kookmin.software.capstone2023.timebank.presentation.api.RequestAttributes
import kookmin.software.capstone2023.timebank.presentation.api.auth.model.UserAuthentication
import kookmin.software.capstone2023.timebank.presentation.api.auth.model.UserContext
import kookmin.software.capstone2023.timebank.presentation.api.v1.model.bank.account.BankAccountCreateRequestData
import kookmin.software.capstone2023.timebank.presentation.api.v1.model.bank.account.BankAccountCreateResponseData
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

    /***
     * @Endpoint: POST /api/v1/bank/account
     * @Description: 은행 계좌 생성 API
     * @RequestBody: BankAccountCreateRequestData
     */
    @PostMapping
    fun createBankAccount(
        @RequestAttribute(RequestAttributes.USER_CONTEXT) userContext: UserContext,
        @Validated @RequestBody
        data: BankAccountCreateRequestData,

    ): BankAccountCreateResponseData {
        val createdBankAccount: BankAccountCreateService.CreatedBankAccount =
            bankAccountCreateService.createBankAccount(
                accountId = userContext.accountId,
                password = data.password,
            )

        return BankAccountCreateResponseData(
            balance = createdBankAccount.balance,
            accountNumber = createdBankAccount.bankAccountNumber, // 계좌번호
            bankAccountId = createdBankAccount.bankAccountId, // 은행 계좌 id
        )
    }

    /***
     * @Endpoint: GET /api/v1/bank/account
     * @Description: 은행 계좌 목록 조회 API
     * @RequestBody: BankAccountReadRequestData
     */
    @GetMapping
    fun readBankAccounts(
        @RequestAttribute(RequestAttributes.USER_CONTEXT) userContext: UserContext,
    ): List<BankAccountReadResponseData> {

        val bankAccountReadResponseDataList: List<BankAccountReadService.ReadedBankAccount> =
            bankAccountReadService.readBankAccountsByAccountId(
                accountId = userContext.accountId,
            )

        return bankAccountReadResponseDataList.map {
            BankAccountReadResponseData(
                bankAccountId = it.bankAccountId,
                branchId = it.branchId,
                balance = it.balance,
                createdAt = it.createdAt,
                bankAccountNumber = it.bankAccountNumber,
                ownerName = it.ownerName,
                ownerType = it.ownerType,
            )
        }
    }

    /***
     * @Endpoint: GET /api/v1/bank/account/{bankAccountNumber}
     * @Description: 은행 계좌 조회 API
     * @RequestParam: bankAccountNumber
     */
    @GetMapping("{bankAccountNumber}")
    fun readBankAccount(
        @RequestAttribute(RequestAttributes.USER_CONTEXT) userContext: UserContext,
        @PathVariable bankAccountNumber: String,
    ): BankAccountReadResponseData {

        val bankAccountReadResponseData: BankAccountReadService.ReadedBankAccount =
            bankAccountReadService.readBankAccountByAccountNumber(
                accountId = userContext.accountId,
                bankAccountNumber = bankAccountNumber,
            )
        
        return BankAccountReadResponseData(
            bankAccountId = bankAccountReadResponseData.bankAccountId,
            branchId = bankAccountReadResponseData.branchId,
            balance = bankAccountReadResponseData.balance,
            createdAt = bankAccountReadResponseData.createdAt,
            bankAccountNumber = bankAccountReadResponseData.bankAccountNumber,
            ownerName = bankAccountReadResponseData.ownerName,
            ownerType = bankAccountReadResponseData.ownerType,
        )
    }
}
