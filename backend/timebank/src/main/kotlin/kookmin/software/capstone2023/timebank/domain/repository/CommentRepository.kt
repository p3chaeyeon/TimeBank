package kookmin.software.capstone2023.timebank.domain.repository

import kookmin.software.capstone2023.timebank.domain.model.Comment
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository : JpaRepository<Comment, Long> {
    fun findByInquiryId(inquiryId: Long): List<Comment>
    fun countByInquiryId(inquiryId: Long): Long
}
