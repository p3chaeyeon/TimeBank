package kookmin.software.capstone2023.timebank.application.service.inqui


import kookmin.software.capstone2023.timebank.application.exception.UnauthorizedException
import kookmin.software.capstone2023.timebank.domain.model.Inquiry
import kookmin.software.capstone2023.timebank.domain.model.InquiryStatus
import kookmin.software.capstone2023.timebank.domain.model.User
import kookmin.software.capstone2023.timebank.domain.repository.InquiryRepository
import kookmin.software.capstone2023.timebank.domain.repository.UserJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class InquiryService(
        private val inquiryRepository: InquiryRepository,
        private val userJpaRepository: UserJpaRepository
) {

    /**
     * Dto 클래스 정의
     */

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

    /**
     * 질문생성Dto
     */
    data class InquiryCreateRequest(
            val title: String,
            val content: String,
            val userId: Long,
            val inquiryDate: LocalDateTime = LocalDateTime.now()
    )

    /**
     * 답변 Dto
     */
    data class InquiryUpdateRequest(
            val replyContent: String?,
            val replyStatus: InquiryStatus?,
            val replyDate: LocalDateTime? = LocalDateTime.now()
    )

    /**
     * 서비스 메소드 정의
     */

    /**
     * 문의 생성 service
     */
    @Transactional
    fun createInquiry(request: InquiryCreateRequest): InquiryDto {
        val user = userJpaRepository.findByIdOrNull(request.userId)
                ?: throw UnauthorizedException(message = "\"User not found with id: ${request.userId}\"")
        val inquiry = Inquiry(title = request.title, content = request.content, user = user, inquiryDate = request.inquiryDate)
        val savedInquiry = inquiryRepository.save(inquiry)
        return inquiryToDto(savedInquiry)
    }

    /**
     * 전체 문의 검색 service
     */
    fun getInquiries(): List<InquiryDto> {
        val inquiries = inquiryRepository.findAll()
        return inquiries.map(::inquiryToDto)
    }

    /**
     * 문의ID검색 service
     */
    fun getInquiryById(id: Long): InquiryDto {
        val inquiry = inquiryRepository.findById(id)
                .orElseThrow { UnauthorizedException(message = "\"Inquiry not found with id: $id\"") }
        return inquiryToDto(inquiry)
    }

    /**
     * userId 검색 service
     */
    fun getInquiriesByUserId(userId: Long): List<InquiryDto> {
        val user = userJpaRepository.findById(userId).orElseThrow { UnauthorizedException(message = "\"User not found with id: $userId\"") }
        val inquiries = inquiryRepository.findByUser(user)
        return inquiries.map { inquiry -> inquiryToDto(inquiry) }
    }

    /**
     * 답변 service
     */
    fun updateInquiry(id: Long, request: InquiryUpdateRequest): InquiryDto {
        val inquiry = inquiryRepository.findById(id)
                .orElseThrow { UnauthorizedException(message = "\"Inquiry not found with id: $id\"") }
        inquiry.replyContent = request.replyContent ?: inquiry.replyContent
        inquiry.replyStatus = request.replyStatus ?: inquiry.replyStatus
        inquiry.replyDate = request.replyDate ?: inquiry.replyDate
        val updatedInquiry = inquiryRepository.save(inquiry)
        return inquiryToDto(updatedInquiry)
    }

    /**
     * 문의삭제 service
     */
    fun deleteInquiryByUserId(userId: Long, inquiryId: Long) {
        val user = userJpaRepository.findById(userId).orElseThrow { UnauthorizedException(message = "\"User not found with id: $userId\"") }
        val inquiry = inquiryRepository.findById(inquiryId).orElseThrow { UnauthorizedException(message = "\"Inquiry not found with id: $inquiryId\"") }

        if (inquiry.user.id != user.id) {
            throw UnauthorizedException(message = "User does not have permission to delete this inquiry")
        }

        inquiryRepository.deleteById(inquiryId)
    }


    /**
     * 유틸 메소드 정의
     */
    private fun inquiryToDto(inquiry: Inquiry): InquiryDto {
        return InquiryDto(
                inquiryid = inquiry.id,
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
