package kookmin.software.capstone2023.timebank.domain.model

import jakarta.persistence.*

@Entity
@Table(name = "account")
class Account(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Embedded
    var profile: AccountProfile,
): BaseTimeEntity()