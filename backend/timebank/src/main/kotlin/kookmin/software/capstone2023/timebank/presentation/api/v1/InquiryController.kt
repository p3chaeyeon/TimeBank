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
     * 문의 조회
     */
    @GetMapping
    fun getInquiries(): ResponseEntity<List<InquiryService.InquiryDto>> {
        val inquiries = inquiryService.getInquiries()
        return ResponseEntity.ok(inquiries)
    }
    /**
     * UserID겁색 조회
     */
    @GetMapping("/{id}")
    fun getInquiryById(@PathVariable id: Long): ResponseEntity<InquiryService.InquiryDto> {
        val inquiry = inquiryService.getInquiryById(id)
        return ResponseEntity.ok(inquiry)
    }

    /**
     * 답변
     */
    @PutMapping("/{id}")
    fun updateInquiry(@PathVariable id: Long, @RequestBody request: InquiryService.InquiryUpdateRequest): ResponseEntity<InquiryService.InquiryDto> {
        val updatedInquiry = inquiryService.updateInquiry(id, request)
        return ResponseEntity.ok(updatedInquiry)
    }
}