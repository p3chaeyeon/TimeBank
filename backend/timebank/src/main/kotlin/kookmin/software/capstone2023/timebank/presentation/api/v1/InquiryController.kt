package kookmin.software.capstone2023.timebank.presentation.api.v1


import kookmin.software.capstone2023.timebank.application.service.inqui.InquiryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import kookmin.software.capstone2023.timebank.domain.repository.InquiryRepository
import kookmin.software.capstone2023.timebank.domain.model.Inquiry
import java.util.*


@RestController
@RequestMapping("/api/v1/inquiries")
class InquiryController(
        private val inquiryService: InquiryService
) {
    /**
     * 문의 작성
     */
    @PostMapping
    fun createInquiry(@RequestBody request: InquiryService.InquiryCreateRequest): ResponseEntity<InquiryService.InquiryDto> {
        val inquiryDto = inquiryService.createInquiry(request)
        return ResponseEntity.ok(inquiryDto)
    }

    /**
     * 문의 전체 조회
     */
    @GetMapping
    fun getInquiries(): ResponseEntity<List<InquiryService.InquiryDto>> {
        val inquiries = inquiryService.getInquiries()
        return ResponseEntity.ok(inquiries)
    }

    /**
     * 문의ID겁색 조회
     */
    @GetMapping("/{id}")
    fun getInquiryById(@PathVariable id: Long): InquiryService.InquiryDto {
        return inquiryService.getInquiryById(id)
    }

    /**
     * userId검색 조회
     */
    @GetMapping("/user/{userId}")
    fun getInquiriesByUserId(@PathVariable userId: Long): List<InquiryService.InquiryDto> {
        return inquiryService.getInquiriesByUserId(userId)
    }

    /**
     * 답변
     */
    @PutMapping("/{id}")
    fun updateInquiry(@PathVariable id: Long, @RequestBody request: InquiryService.InquiryUpdateRequest): InquiryService.InquiryDto {
        return inquiryService.updateInquiry(id, request)
    }

    /**
     * 문의 삭제
     */
    @DeleteMapping("/user/{userId}/{inquiryId}")
    fun deleteInquiryByUserId(@PathVariable userId: Long, @PathVariable inquiryId: Long): ResponseEntity<Void> {
        inquiryService.deleteInquiryByUserId(userId, inquiryId)
        return ResponseEntity.noContent().build()
    }

    /**
     * 재문의
     */
    @PostMapping("/{id}/reopen")
    fun reopenInquiry(
            @PathVariable id: Long,
            @RequestBody request: InquiryService.InquiryReopenRequest
    ): ResponseEntity<InquiryService.InquiryDto> {
        val reopenedInquiryDto = inquiryService.reopenInquiry(id, request)
        return ResponseEntity.ok(reopenedInquiryDto)
    }

    /**
     * 재답변
     */
    @PutMapping("/{id}/reply")
    fun replyToInquiry(
            @PathVariable id: Long,
            @RequestBody request: InquiryService.InquiryReplyRequest
    ): InquiryService.InquiryDto {
        return inquiryService.replyToInquiry(id, request)
    }
}