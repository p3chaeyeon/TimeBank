package kookmin.software.capstone2023.timebank.application.service.inqui


import kookmin.software.capstone2023.timebank.domain.model.Inquiry
import kookmin.software.capstone2023.timebank.domain.model.InquiryStatus
import kookmin.software.capstone2023.timebank.domain.model.User
import kookmin.software.capstone2023.timebank.domain.repository.InquiryRepository
import kookmin.software.capstone2023.timebank.domain.repository.UserJpaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class InquiryService(
        private val inquiryRepository: InquiryRepository,
        private val userJpaRepository: UserJpaRepository
) {

    // Dto 클래스 정의
    data class InquiryDto(
            val inquiryid: Long,
            val title: String,
            val content: String,
            val replyContent: String?,
            val inquiryDate: LocalDateTime,
            val replyDate: LocalDateTime?,
            val replyStatus: InquiryStatus,
            val userId: Long
    )

    data class InquiryCreateRequest(
            val title: String,
            val content: String,
            val userId: Long,
            val inquiryDate: LocalDateTime = LocalDateTime.now()
    )

    data class InquiryUpdateRequest(
            val replyContent: String?,
            val replyStatus: InquiryStatus?,
            val replyDate: LocalDateTime? = LocalDateTime.now()
    )

    // 서비스 메소드 정의
    @Transactional
    fun createInquiry(request: InquiryCreateRequest): InquiryDto {
        val user = userJpaRepository.findById(request.userId)
                .orElseThrow { IllegalArgumentException("User not found with id: ${request.userId}") }
        val inquiry = Inquiry(title = request.title, content = request.content, user = user, inquiryDate = request.inquiryDate)
        val savedInquiry = inquiryRepository.save(inquiry)
        return inquiryToDto(savedInquiry)
    }
    fun getInquiries(): List<InquiryDto> {
        val inquiries = inquiryRepository.findAll()
        return inquiries.map(::inquiryToDto)
    }

    fun getInquiryById(id: Long): InquiryDto {
        val inquiry = inquiryRepository.findById(id)
                .orElseThrow { IllegalArgumentException("Inquiry not found with id: $id") }
        return inquiryToDto(inquiry)
    }

    fun updateInquiry(id: Long, request: InquiryUpdateRequest): InquiryDto {
        val inquiry = inquiryRepository.findById(id)
                .orElseThrow { IllegalArgumentException("Inquiry not found with id: $id") }
        inquiry.replyContent = request.replyContent ?: inquiry.replyContent
        inquiry.replyStatus = request.replyStatus ?: inquiry.replyStatus
        inquiry.replyDate = request.replyDate ?: inquiry.replyDate
        val updatedInquiry = inquiryRepository.save(inquiry)
        return inquiryToDto(updatedInquiry)
    }

    // 유틸리티 메소드 정의
    private fun inquiryToDto(inquiry: Inquiry): InquiryDto {
        return InquiryDto(
                inquiryid = inquiry.inquiryid,
                title = inquiry.title,
                content = inquiry.content,
                replyContent = inquiry.replyContent,
                inquiryDate = inquiry.inquiryDate,
                replyDate = inquiry.replyDate,
                replyStatus = inquiry.replyStatus,
                userId = inquiry.user.id
        )
    }
}
