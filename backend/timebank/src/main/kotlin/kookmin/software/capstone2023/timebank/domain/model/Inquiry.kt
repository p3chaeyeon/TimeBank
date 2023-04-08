package kookmin.software.capstone2023.timebank.domain.model

import java.time.LocalDateTime
import jakarta.persistence.*

@Entity
@Table(name = "inquiry")
class Inquiry(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,

        @Column(nullable = false)
        var title: String,

        @Column(nullable = false, columnDefinition = "TEXT")
        var content: String,

        @Column(nullable = false)
        var inquiryDate: LocalDateTime = LocalDateTime.now(),

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        var replyStatus: InquiryStatus = InquiryStatus.PENDING,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id", nullable = false)
        val user: User,

        

){
        @Column(name = "user_id", insertable = false, updatable = false)
        val userId: Long = user.id
}


