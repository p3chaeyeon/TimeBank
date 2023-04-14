package kookmin.software.capstone2023.timebank.presentation.api.v1

import kookmin.software.capstone2023.timebank.application.exception.UnauthorizedException
import kookmin.software.capstone2023.timebank.application.service.bank.account.BankAccountReadService
import kookmin.software.capstone2023.timebank.application.service.bank.account.transaction.BankAccountTransactionReadService
import kookmin.software.capstone2023.timebank.domain.model.BankAccountTransaction
import kookmin.software.capstone2023.timebank.presentation.api.RequestAttributes
import kookmin.software.capstone2023.timebank.presentation.api.auth.model.UserContext
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/v1/bank/transaction")
@RestController
class BankAccountTransactionController(
    private val bankAccountTransactionReadService: BankAccountTransactionReadService,
    private val bankAccountReadService: BankAccountReadService
) {
    @GetMapping("/transactions")
    fun getBankAccountTransactionsByBankAccountNumber(
        @RequestAttribute(RequestAttributes.USER_CONTEXT) userContext: UserContext,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestBody accountNumber: String,
    ): List<BankAccountTransaction> {

        val bankAccount = bankAccountReadService.getBankAccountByAccountNumberAndAccountId(accountNumber, userContext.accountId)

        // 페이징 처리 생성일자로 내림차순
        val pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt")
        val bankAccountTransactionPage = bankAccountTransactionReadService.getBankAccountTransactionByAccountId(bankAccount.id, pageable)

        return bankAccountTransactionPage.content
    }
}