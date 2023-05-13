package kookmin.software.capstone2023.timebank.presentation.api.v1

import kookmin.software.capstone2023.timebank.application.exception.NotFoundException
import kookmin.software.capstone2023.timebank.application.service.account.AccountFinder
import kookmin.software.capstone2023.timebank.application.service.auth.AccountLoginService
import kookmin.software.capstone2023.timebank.application.service.auth.AccountRegisterService
import kookmin.software.capstone2023.timebank.application.service.user.UserFinder
import kookmin.software.capstone2023.timebank.application.service.user.UserUpdateService
import kookmin.software.capstone2023.timebank.application.service.user.UserWithdrawalService
import kookmin.software.capstone2023.timebank.domain.model.AccountType
import kookmin.software.capstone2023.timebank.presentation.api.RequestAttributes
import kookmin.software.capstone2023.timebank.presentation.api.auth.model.UserAuthentication
import kookmin.software.capstone2023.timebank.presentation.api.auth.model.UserContext
import kookmin.software.capstone2023.timebank.presentation.api.v1.model.*
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/users")
class UserController(
    private val accountFinder: AccountFinder,
    private val accountLoginService: AccountLoginService,
    private val accountRegisterService: AccountRegisterService,
    private val userFinder: UserFinder,
    private val userUpdateService: UserUpdateService,
    private val userWithdrawalService: UserWithdrawalService,
) {
    @PostMapping("login")
    fun loginUser(
        @Validated @RequestBody
        data: UserLoginRequestData,
    ): UserLoginResponseData {
        val loginData = accountLoginService.login(data.toAuthenticationRequest())

        return UserLoginResponseData(
            accessToken = loginData.accessToken,
        )
    }

    @PostMapping("register")
    @ResponseStatus(HttpStatus.CREATED)
    fun registerUser(
        @Validated @RequestBody
        data: UserRegisterRequestData,
    ) {
        accountRegisterService.register(
            authentication = data.toAuthenticationRequest(),
            name = data.name,
            phoneNumber = data.phoneNumber,
            accountType = AccountType.INDIVIDUAL,
        )
    }

    @UserAuthentication
    @GetMapping("me")
    fun getCurrentUser(
        @RequestAttribute(RequestAttributes.USER_CONTEXT)
        userContext: UserContext,
    ): CurrentUserResponseData {
        val user = userFinder.findById(userContext.userId)
            ?: throw NotFoundException(message = "유저 정보를 찾을 수 없습니다.")

        val account = accountFinder.findById(user.accountId)
            ?: throw NotFoundException(message = "계정 정보를 찾을 수 없습니다.")

        return CurrentUserResponseData(
            id = user.id,
            name = user.name,
            phoneNumber = user.phoneNumber,
            account = AccountResponseData.fromDomain(account),
        )
    }

    @UserAuthentication
    @PutMapping("{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updateUserInfo(
        @RequestAttribute(RequestAttributes.USER_CONTEXT)
        userContext: UserContext,
        @RequestBody
        data: UserUpdateRequestData,
    ) {
        userUpdateService.updateUserInfo(
            userId = userContext.userId,
            name = data.name,
            phoneNumber = data.phoneNumber,
        )
    }

    @UserAuthentication
    @PutMapping("{userId}/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updateUserPassword(
        @RequestAttribute(RequestAttributes.USER_CONTEXT)
        userContext: UserContext,
        @RequestBody
        data: UserUpdatePasswordRequestData,
    ) {
        userUpdateService.updatePassword(
            userId = userContext.userId,
            password = data.password,
        )
    }

    @UserAuthentication
    @DeleteMapping("{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun withdrawalUser(
        @RequestAttribute(RequestAttributes.USER_CONTEXT)
        userContext: UserContext,
    ) {
        userWithdrawalService.withdrawal(userContext.userId)
    }
}
