package kookmin.software.capstone2023.timebank.presentation.api.v1

import kookmin.software.capstone2023.timebank.application.service.inqui.CommentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import kookmin.software.capstone2023.timebank.domain.repository.CommentRepository
import kookmin.software.capstone2023.timebank.domain.model.Comment
import java.util.*

@RestController
@RequestMapping("/api/v1/inquiries/{id}/comments")
class CommentController(
        private val commentService: CommentService
) {
    /**
     * 댓글 작성(User)
     */
    @PostMapping
    fun createComment(@PathVariable id: Long, @RequestBody request: CommentService.CommentCreateRequest): CommentService.CommentDto {
        return commentService.createComment(id, request)
    }

    /**
     * 답변 작성(Admin)

    @PostMapping
    fun createReply(@PathVariable id: Long, @RequestBody request: CommentService.CommentReplyRequest): CommentService.CommentDto {
        return commentService.createReply(request)
    }
     */

    /**
     * 전체 댓글 조회

    @GetMapping
    fun getComments(): ResponseEntity<List<CommentService.CommentDto>> {
        val comments = commentService.getComments()
        return ResponseEntity.ok(comments)
    }
     */
    /**
     * 문의Id 댓글 조회
     */
    @GetMapping
    fun getCommentsByInquiryId(@PathVariable id: Long): List<CommentService.CommentDto> {
        return commentService.getCommentByInquiryId(id)
    }


    /**
     * 댓글 수정
     */
    @PutMapping("/{commentId}")
    fun updateComment(@PathVariable id: Long, @PathVariable commentId: Long, @RequestBody request: CommentService.CommentUpdateRequest): CommentService.CommentDto {
        return commentService.updateComment(commentId, request)
    }

    /**
     * 댓글 삭제
     */
    @DeleteMapping("/{commentId}")
    fun deleteCommentById(@PathVariable commentId: Long, @PathVariable id: Long): ResponseEntity<Unit> {
        commentService.deleteCommentByCommentId(id, commentId)
        return ResponseEntity.noContent().build()
    }

}