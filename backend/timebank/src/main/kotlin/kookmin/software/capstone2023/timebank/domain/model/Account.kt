package kookmin.software.capstone2023.timebank.domain.model

import jakarta.persistence.*

@Entity
@Table(name = "account")
class Account(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, updatable = false, length = 20)
    @Enumerated(EnumType.STRING)
    val type: AccountType,

    @Embedded
    var profile: AccountProfile? = null,
): BaseTimeEntity()