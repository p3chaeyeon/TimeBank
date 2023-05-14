package kookmin.software.capstone2023.timebank.application.service.user

import kookmin.software.capstone2023.timebank.application.exception.NotFoundException
import kookmin.software.capstone2023.timebank.domain.model.Account
import kookmin.software.capstone2023.timebank.domain.model.AccountType
import kookmin.software.capstone2023.timebank.domain.model.Gender
import kookmin.software.capstone2023.timebank.domain.model.User
import kookmin.software.capstone2023.timebank.domain.model.auth.PasswordAuthentication
import kookmin.software.capstone2023.timebank.domain.repository.AccountJpaRepository
import kookmin.software.capstone2023.timebank.domain.repository.PasswordAuthenticationJpaRepository
import kookmin.software.capstone2023.timebank.domain.repository.UserJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class UserUpdateService(
    private val userJpaRepository: UserJpaRepository,
    private val accountJpaRepository: AccountJpaRepository,
    private val passwordAuthenticationJpaRepository: PasswordAuthenticationJpaRepository,
    private val passwordEncoder: PasswordEncoder,
) {
    @Transactional
    fun updateUserInfo(
        userId: Long,
        name: String,
        phoneNumber: String,
        gender: Gender,
        birthday: LocalDate,
    ) {
        val user: User = userJpaRepository.findByIdOrNull(userId)
            ?: throw NotFoundException(message = "사용자를 찾을 수 없습니다.")

        val account: Account = accountJpaRepository.findByIdOrNull(user.accountId)
            ?: throw NotFoundException(message = "계정을 찾을 수 없습니다.")

        user.updateUserInfo(
            name = name,
            phoneNumber = phoneNumber,
            gender = gender,
            birthday = birthday,
        )

        // 개인 계정의 경우 계정 이름 변경
        if (account.type == AccountType.INDIVIDUAL) {
            account.updateName(
                name = name,
            )
        }
    }

    @Transactional
    fun updatePassword(
        userId: Long,
        password: String,
    ) {
        userJpaRepository.findByIdOrNull(userId)
            ?: throw NotFoundException(message = "사용자를 찾을 수 없습니다.")

        val passwordAuthentication: PasswordAuthentication = passwordAuthenticationJpaRepository.findByUserId(userId)
            ?: throw NotFoundException(message = "비밀번호 인증 정보를 찾을 수 없습니다.")

        passwordAuthentication.updatePassword(
            password = passwordEncoder.encode(password),
        )

        passwordAuthenticationJpaRepository.save(passwordAuthentication)
    }
}
