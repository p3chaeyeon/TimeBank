package kookmin.software.capstone2023.timebank.domain.repository

import jakarta.persistence.Id
import kookmin.software.capstone2023.timebank.domain.model.Comment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import kookmin.software.capstone2023.timebank.domain.model.Inquiry
import kookmin.software.capstone2023.timebank.domain.model.User
import java.util.Optional

interface CommentRepository : JpaRepository<Comment, Long>{
    fun findByUser(userId: Long): List<Comment>
    fun findByInquiryId(inquiryId: Long): List<Comment>
    fun countByInquiryId(inquiryId: Long): Long
}