package kookmin.software.capstone2023.timebank.domain.model

import java.time.LocalDateTime
import jakarta.persistence.*
@Entity
@Table(name = "comments")
data class Comment(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,

        @Column(nullable = false)
        val commentSeq: Long,

        @Column(nullable = false, columnDefinition = "TEXT")
        var content: String,

        @Column(nullable = false)
        var commentDate: LocalDateTime = LocalDateTime.now(),

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        var replyStatus: InquiryStatus = InquiryStatus.PENDING,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "inquiry_id", nullable = false)
        val inquiry: Inquiry,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id", nullable = false)
        val user: User
){
    @Column(name = "user_id", insertable = false, updatable = false)
    val userId: Long = user.id

    @Column(name = "inquiry_id", insertable = false, updatable = false)
    val inquiryId : Long = inquiry.id
}