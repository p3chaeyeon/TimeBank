package kookmin.software.capstone2023.timebank.infrastructure.security
import kookmin.software.capstone2023.timebank.domain.model.security.FailedAttempts
import kookmin.software.capstone2023.timebank.domain.repository.security.FailedAttemptsRepository
import org.springframework.stereotype.Component

@Component
class FailedAttemptsCounter(
    private val failedAttemptsRepository: FailedAttemptsRepository,
) {
    fun incrementFailedAttempts(ipAddress: String) {
        val failedAttempts = failedAttemptsRepository.findByIpAddress(ipAddress)
        if (failedAttempts != null) {
            failedAttempts.attempts++
            failedAttemptsRepository.save(failedAttempts)
        } else {
            failedAttemptsRepository.save(FailedAttempts(ipAddress, 1))
        }
    }

    fun getFailedAttempts(ipAddress: String): Int {
        val failedAttempts = failedAttemptsRepository.findByIpAddress(ipAddress)
        return failedAttempts?.attempts ?: 0
    }

    fun resetFailedAttempts(ipAddress: String) {
        failedAttemptsRepository.deleteById(ipAddress)
    }
}
