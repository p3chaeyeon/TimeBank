package kookmin.software.capstone2023.timebank.domain.repository.security

import kookmin.software.capstone2023.timebank.domain.model.security.FailedAttempts
import org.springframework.data.jpa.repository.JpaRepository

interface FailedAttemptsRepository : JpaRepository<FailedAttempts, String> {
    fun findByIpAddress(ipAddress: String): FailedAttempts?
}
