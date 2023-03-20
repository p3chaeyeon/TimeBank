package kookmin.software.capstone2023.timebank.presentation.api.v1

import kookmin.software.capstone2023.timebank.application.service.auth.UserLoginService
import kookmin.software.capstone2023.timebank.presentation.api.v1.model.LoginUserRequestData
import kookmin.software.capstone2023.timebank.presentation.api.v1.model.LoginUserResponseData
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/auth")
class AuthController(
    private val userLoginService: UserLoginService,
) {
    @PostMapping("login")
    fun loginUser(
        @Validated @RequestBody data: LoginUserRequestData.SocialLoginUserRequestData,
    ): LoginUserResponseData {
        val loginData = userLoginService.socialLogin(
            provider = data.provider,
            accessToken = data.accessToken,
        )

        return LoginUserResponseData(
            accessToken = loginData.accessToken,
        )
    }
}