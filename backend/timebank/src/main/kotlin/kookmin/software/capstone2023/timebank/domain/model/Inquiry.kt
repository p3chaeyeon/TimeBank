package kookmin.software.capstone2023.timebank.domain.model

import java.time.LocalDateTime
import jakarta.persistence.*

@Entity
@Table(name = "inquiries")
class Inquiry(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val inquiryid: Long = 0,

        @Column(nullable = false)
        val title: String,

        @Column(nullable = false, columnDefinition = "TEXT")
        val content: String,

        @Column(columnDefinition = "TEXT")
        var replyContent: String? = null,

        @Column(nullable = false)
        val inquiryDate: LocalDateTime = LocalDateTime.now(),

        var replyDate: LocalDateTime? = null,

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        var replyStatus: InquiryStatus = InquiryStatus.PENDING,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id", nullable = false)
        val user: User,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "parent_inquiry_id")
        val parentInquiry: Inquiry? = null
)

enum class InquiryStatus {
    PENDING,
    ANSWERED,
    REOPENED
}
