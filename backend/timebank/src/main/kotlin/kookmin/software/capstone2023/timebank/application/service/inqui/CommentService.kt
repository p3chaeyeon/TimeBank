package kookmin.software.capstone2023.timebank.application.service.inqui

import kookmin.software.capstone2023.timebank.application.exception.UnauthorizedException
import kookmin.software.capstone2023.timebank.domain.model.Comment
import kookmin.software.capstone2023.timebank.domain.model.InquiryStatus
import kookmin.software.capstone2023.timebank.domain.repository.CommentRepository
import kookmin.software.capstone2023.timebank.domain.repository.InquiryRepository
import kookmin.software.capstone2023.timebank.domain.repository.UserJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class CommentService(
    private val inquiryRepository: InquiryRepository,
    private val userJpaRepository: UserJpaRepository,
    private val commentRepository: CommentRepository,
) {
    /**
     * Dto클래스
     */
    data class CommentDto(
        val commentid: Long,
        val commentSeq: Long,
        var content: String,
        val commentDate: LocalDateTime,
        val replyStatus: InquiryStatus,
        val userId: Long,
        val inquiryId: Long,
    )

    /**
     * 댓글 생성 Dto(User)
     */
    data class CommentCreateRequest(
        val content: String,
        val userId: Long,
        val inquiryId: Long,
        val commentDate: LocalDateTime = LocalDateTime.now(),
    )

    /**
     * 댓글 생성Dto(Admin Reply)
     */
    data class CommentReplyRequest(
        val content: String,
        val userId: Long,
        val inquiryId: Long,
        val commentDate: LocalDateTime = LocalDateTime.now(),
        val replyStatus: InquiryStatus = InquiryStatus.ANSWERED,
    )

    /**
     * 댓글 수정 Dto
     */
    data class CommentUpdateRequest(
        val content: String?,
        val commentDate: LocalDateTime? = LocalDateTime.now(),
    )

    /**
     * 댓글 생성 service (user)
     */
    @Transactional
    fun createComment(inquiryId: Long, request: CommentCreateRequest): CommentDto {
        val user = userJpaRepository.findByIdOrNull(request.userId)
            ?: throw UnauthorizedException(message = "\"User not found with id: ${request.userId}\"")
        val inquiry = inquiryRepository.findByIdOrNull(request.inquiryId)
            ?: throw UnauthorizedException(message = "\"User not found with id: ${request.inquiryId}\"")
        val previousCommentCount = commentRepository.countByInquiryId(request.inquiryId)
        val newCommentId = previousCommentCount + 1
        val comment = Comment(
            content = request.content,
            user = user,
            inquiry = inquiry,
            commentDate = request.commentDate,
            commentSeq = newCommentId,
        )
        val savedComment = commentRepository.save(comment)
        return commentToDto(savedComment)
    }

    /**
     * 전체 댓글 조회 service(필요한지?)
     */
    fun getComments(): List<CommentDto> {
        val comments = commentRepository.findAll()
        return comments.map(::commentToDto)
    }

    /**
     * 문의 id 검색 service
     */
    fun getCommentByInquiryId(id: Long): List<CommentDto> {
        val comments = commentRepository.findByInquiryId(id)
        return comments.map { comment -> commentToDto(comment) }
    }

    /**
     * 댓글 수정 service
     */
    fun updateComment(id: Long, request: CommentUpdateRequest): CommentDto {
        val comment = commentRepository.findById(id)
            .orElseThrow { UnauthorizedException(message = "\"Comment not found with id: $id\"") }
        comment.content = request.content ?: comment.content
        comment.commentDate = request.commentDate ?: comment.commentDate
        val updatedComment = commentRepository.save(comment)
        return commentToDto(updatedComment)
    }

    /**
     * 댓글 삭제 service
     */
    fun deleteCommentByCommentId(inquiryId: Long, commentId: Long) {
        // val user = userJpaRepository.findById(userId).orElseThrow { UnauthorizedException(message = "\"User not found with id: $userId\"") }
        val inquiry = inquiryRepository.findById(inquiryId).orElseThrow {
            UnauthorizedException(
                message = "\"Inquiry not found with id: $inquiryId\"",
            )
        }
        val comment = commentRepository.findById(commentId).orElseThrow {
            UnauthorizedException(
                message = "\"Comment not found with id: $commentId\"",
            )
        }
        if (comment.inquiry.id != inquiry.id) {
            throw UnauthorizedException(message = "User does not have permission to delete this inquiry")
        }

        commentRepository.deleteById(commentId)
    }

    private fun commentToDto(comment: Comment): CommentDto {
        return CommentDto(
            commentid = comment.id,
            content = comment.content,
            commentDate = comment.commentDate,
            replyStatus = comment.replyStatus,
            inquiryId = comment.inquiryId,
            userId = comment.userId,
            commentSeq = comment.commentSeq,
        )
    }
}
