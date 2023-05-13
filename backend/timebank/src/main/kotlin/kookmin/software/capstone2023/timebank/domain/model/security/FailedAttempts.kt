package kookmin.software.capstone2023.timebank.domain.model.security

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "failed_attempts")
class FailedAttempts(
    @Id
    val ipAddress: String,
    var attempts: Int = 0,
)
