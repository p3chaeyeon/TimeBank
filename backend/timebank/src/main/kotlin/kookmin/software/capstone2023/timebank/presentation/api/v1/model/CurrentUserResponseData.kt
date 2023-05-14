package kookmin.software.capstone2023.timebank.presentation.api.v1.model

import kookmin.software.capstone2023.timebank.domain.model.Gender
import java.time.LocalDate

data class CurrentUserResponseData(
    val id: Long,
    val name: String,
    val phoneNumber: String,
    val gender: Gender,
    val birthday: LocalDate,
    val account: AccountResponseData,
)
