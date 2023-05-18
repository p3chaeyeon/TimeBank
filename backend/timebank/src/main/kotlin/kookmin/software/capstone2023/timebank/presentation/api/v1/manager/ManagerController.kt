package kookmin.software.capstone2023.timebank.presentation.api.v1.manager

import kookmin.software.capstone2023.timebank.application.service.auth.AccountLoginService
import kookmin.software.capstone2023.timebank.domain.repository.BankAccountJpaRepository
import kookmin.software.capstone2023.timebank.domain.repository.spec.BankAccountSpecs
import kookmin.software.capstone2023.timebank.presentation.api.v1.manager.model.BankAccountResponseData
import kookmin.software.capstone2023.timebank.presentation.api.v1.manager.model.ManagerLoginRequestData
import kookmin.software.capstone2023.timebank.presentation.api.v1.manager.model.ManagerLoginResponseData
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.web.PageableDefault
import org.springframework.transaction.annotation.Transactional
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@RequestMapping("api/v1/managers")
class ManagerController(
    private val accountLoginService: AccountLoginService,
    private val bankAccountJpaRepository: BankAccountJpaRepository,
) {
    @PostMapping("login")
    fun loginManager(
        @Validated @RequestBody
        data: ManagerLoginRequestData,
    ): ManagerLoginResponseData {
        val loginData = accountLoginService.login(data.toAuthenticationRequest())

        return ManagerLoginResponseData(
            accessToken = loginData.accessToken,
        )
    }

    @GetMapping("bank-accounts")
    @Transactional(readOnly = true)
    fun listBankAccount(
        @RequestParam(required = false) bankAccountNumber: String?,
        @RequestParam(required = false) userId: Long?,
        @RequestParam(required = false) userName: String?,
        @RequestParam(required = false) userPhoneNumber: String?,
        @RequestParam(required = false) userBirthday: LocalDate?,
        @PageableDefault pageable: Pageable,
    ): Page<BankAccountResponseData> {
        return bankAccountJpaRepository.findAll(
            Specification.allOf(
                bankAccountNumber?.let { BankAccountSpecs.withAccountNumber(bankAccountNumber) },
                BankAccountSpecs.withUser(
                    id = userId,
                    name = userName,
                    phoneNumber = userPhoneNumber,
                    birthday = userBirthday,
                ),
            ),
            pageable,
        ).map {
            BankAccountResponseData.fromDomain(it)
        }
    }
}
