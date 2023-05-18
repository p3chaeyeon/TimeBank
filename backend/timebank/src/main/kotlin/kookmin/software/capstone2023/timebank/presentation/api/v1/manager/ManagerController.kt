package kookmin.software.capstone2023.timebank.presentation.api.v1.manager

import kookmin.software.capstone2023.timebank.application.service.auth.AccountLoginService
import kookmin.software.capstone2023.timebank.presentation.api.v1.manager.model.ManagerLoginRequestData
import kookmin.software.capstone2023.timebank.presentation.api.v1.manager.model.ManagerLoginResponseData
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/managers")
class ManagerController(
    private val accountLoginService: AccountLoginService,
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
}
