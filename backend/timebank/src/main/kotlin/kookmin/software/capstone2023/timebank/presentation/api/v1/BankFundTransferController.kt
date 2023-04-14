package kookmin.software.capstone2023.timebank.presentation.api.v1

import kookmin.software.capstone2023.timebank.application.service.bank.transfer.TransferService
import kookmin.software.capstone2023.timebank.application.service.bank.transfer.TransferServiceImpl
import kookmin.software.capstone2023.timebank.presentation.api.RequestAttributes
import kookmin.software.capstone2023.timebank.presentation.api.auth.model.UserContext
import kookmin.software.capstone2023.timebank.presentation.api.v1.model.BankFundTransferRequestData
import kookmin.software.capstone2023.timebank.presentation.api.v1.model.BankFundTransferResponseData
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/bank/fund-transfer")
class BankFundTransferController(
    private val fundTransferService: TransferServiceImpl,
) {
    @PostMapping
    fun transfer(
        @RequestAttribute(RequestAttributes.USER_CONTEXT) userContext: UserContext,
        @Validated @RequestBody
        data: BankFundTransferRequestData,
    ): BankFundTransferResponseData {
        val response = fundTransferService.transfer(
            TransferService.TransferRequest(
                senderAccountNumber = data.senderBankAccountNumber,
                receiverAccountNumber = data.receiverBankAccountNumber,
                amount = data.amount,
                password = data.password,
            ),
        )
        return BankFundTransferResponseData(
            transactionId = response.id,
            transactionAt = response.transactionAt,
            amount = response.amount,
            balanceSnapshot = response.balanceSnapshot,
            status = response.status,
            senderBankAccountNumber = response.senderAccountNumber,
            receiverBankAccountNumber = response.receiverAccountNumber,
        )
    }
}
