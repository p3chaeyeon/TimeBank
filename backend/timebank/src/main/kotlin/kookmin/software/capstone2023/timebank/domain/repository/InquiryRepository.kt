package kookmin.software.capstone2023.timebank.domain.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import kookmin.software.capstone2023.timebank.domain.model.Inquiry
import kookmin.software.capstone2023.timebank.domain.model.User
import java.util.Optional
import java.time.LocalDateTime

@Repository
interface InquiryRepository : JpaRepository<Inquiry, Long>{
    fun findByUser(user: User): List<Inquiry>
    fun findByUserId(userId: Long): List<Inquiry>
    fun findByTitle(title: String): List<Inquiry>
    fun findByCreatedAtBetween(start: LocalDateTime, end: LocalDateTime): List<Inquiry>
}
