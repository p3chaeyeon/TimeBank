package kookmin.software.capstone2023.timebank.presentation.api.v1

import kookmin.software.capstone2023.timebank.application.exception.UnauthorizedException
import kookmin.software.capstone2023.timebank.application.service.inqui.InquiryService
import kookmin.software.capstone2023.timebank.domain.model.AccountType
import kookmin.software.capstone2023.timebank.domain.model.Period
import kookmin.software.capstone2023.timebank.presentation.api.RequestAttributes
import kookmin.software.capstone2023.timebank.presentation.api.auth.model.UserContext
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/inquiries")
class InquiryController(
    private val inquiryService: InquiryService,
) {
    /**
     * 문의 작성
     */
    @PostMapping
    fun createInquiry(
        @RequestAttribute(RequestAttributes.USER_CONTEXT) userContext: UserContext,
        @RequestBody request: InquiryService.InquiryCreateRequest
    ): InquiryService.InquiryDto {
        return inquiryService.createInquiry(request, userContext)
    }

    /**
     * 문의 전체 조회
     */
    @GetMapping
    fun getInquiries(
        @RequestAttribute(RequestAttributes.USER_CONTEXT) userContext: UserContext,
    ): List<InquiryService.InquiryDto> {
        if (userContext.accountType != AccountType.BRANCH) {
            throw UnauthorizedException(message = "접근 권한이 없습니다.")
        }
        return inquiryService.getInquiries()
    }

    /**
     * 문의ID겁색 조회
     *
     */
    @GetMapping("/{id}")
    fun getInquiryById(
        @RequestAttribute(RequestAttributes.USER_CONTEXT) userContext: UserContext,
        @PathVariable id: Long,
    ): InquiryService.InquiryDto {
        if (userContext.accountType != AccountType.BRANCH) {
            throw UnauthorizedException(message = "접근 권한이 없습니다.")
        }
        return inquiryService.getInquiryById(id)
    }

    /**
     * userId검색 조회
     */
    @GetMapping("/users/{userId}")
    fun getInquiriesByUserId(
        @RequestAttribute(RequestAttributes.USER_CONTEXT) userContext: UserContext,
        @PathVariable userId: Long,
    ): List<InquiryService.InquiryDto> {
        if (userContext.accountType == AccountType.INDIVIDUAL) {
            if (userContext.userId != userId) {
                throw UnauthorizedException(message = "접근권한이 없습니다.")
            }
        }
        return inquiryService.getInquiriesByUserId(userId)
    }

    /**
     * 문의 기간 조회 for brnach
     */
    @GetMapping("/period")
    fun getInquiriesByPeriod(@RequestParam("period") period: Period): List<InquiryService.InquiryDto> {
        return inquiryService.getInquiriesByPeriod(period)
    }

    /**
     * 문의 기간 조회 for user
     */
    @GetMapping("/users/{userId}/period")
    fun getUserInquiriesByPeriod(
        @RequestAttribute(RequestAttributes.USER_CONTEXT) userContext: UserContext,
        @RequestParam("period") period: Period,
        @PathVariable userId: Long,
    ): List<InquiryService.InquiryDto> {
        return inquiryService.getUserInquiriesByPeriod(period, userId)
    }

    /**
     * 문의 제목 조회 for branch
     */
    @GetMapping("/search")
    fun getInquiriesByTitle(
        @RequestParam("title") title: String,
    ): List<InquiryService.InquiryDto> {
        return inquiryService.getInquiryByTitle(title)
    }

    /**
     * 문의 조회 (동시 조건 검색)
     */
    @GetMapping("/multisearch")
    fun searchInquiries(
        @RequestParam("title", required = false) title: String?,
        @RequestParam("period", required = false) period: Period?,
        @RequestParam("userId", required = false) userId: Long?,
    ): List<InquiryService.InquiryDto> {
        return inquiryService.searchInquiries(title, period, userId)
    }

    /**
     * 문의 제목 조회 for user
     */
    @GetMapping("/users/{userId}/search")
    fun getUserInquiriesByTitle(
        @RequestAttribute(RequestAttributes.USER_CONTEXT) userContext: UserContext,
        @RequestParam("title") title: String,
        @PathVariable userId: Long,
    ): List<InquiryService.InquiryDto> {
        if (userContext.userId != userId) {
            throw UnauthorizedException(message = "접근 권한이 없습니다.")
        }
        return inquiryService.getUserInquiryByTitle(title, userId)
    }

    /**
     * 문의 수정
     */
    @PutMapping("/users/{userId}/{id}")
    fun updateInquiry(
        @RequestAttribute(RequestAttributes.USER_CONTEXT) userContext: UserContext,
        @PathVariable id: Long,
        @PathVariable userId: Long,
        @RequestBody request: InquiryService.InquiryUpdateRequest,
    ): InquiryService.InquiryDto {
        if (userContext.userId != userId) {
            throw UnauthorizedException(message = "수정 권한이 없습니다.")
        }
        return inquiryService.updateInquiry(id, request)
    }

    /**
     * 문의 삭제
     */
    @DeleteMapping("/users/{userId}/{inquiryId}")
    fun deleteInquiryByUserId(
        @PathVariable userId: Long,
        @PathVariable inquiryId: Long,
    ): ResponseEntity<Unit> {
        inquiryService.deleteInquiryByUserId(userId, inquiryId)
        return ResponseEntity.noContent().build()
    }

    /**
     * 문의 상태 변경 for branch
     */
    @PutMapping("/{id}/status")
    fun updateInquiryStatus(
        @RequestAttribute(RequestAttributes.USER_CONTEXT) userContext: UserContext,
        @PathVariable id: Long,
        @RequestBody request: InquiryService.InquiryStatusUpdateRequest,
    ): InquiryService.InquiryDto {
        if (userContext.accountType != AccountType.BRANCH) {
            throw UnauthorizedException(message = "접근 권한이 없습니다.")
        }
        return inquiryService.updateInquiryStatus(id, request.status)
    }
}
