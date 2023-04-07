package kookmin.software.capstone2023.timebank.domain.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "BankBranch")
data class BankBranch(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val name: String,

    ):BaseTimeEntity()