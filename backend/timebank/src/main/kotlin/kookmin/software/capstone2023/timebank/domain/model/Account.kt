package kookmin.software.capstone2023.timebank.domain.model

import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

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
) : BaseTimeEntity()
