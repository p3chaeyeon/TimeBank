package kookmin.software.capstone2023.timebank.domain.model

enum class Period(val months: Long) {
    ONE_MONTH(1),
    THREE_MONTHS(3),
    SIX_MONTHS(6),
    ONE_YEAR(12)
}