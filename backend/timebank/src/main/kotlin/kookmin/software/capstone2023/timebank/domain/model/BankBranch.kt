package kookmin.software.capstone2023.timebank.domain.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "bank_branch")
data class BankBranch(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(length = 20, nullable = false, updatable = true)
    val name: String,

    @OneToMany(mappedBy = "branch")
    val bankAccounts: Set<BankAccount> = emptySet(),
) : BaseTimeEntity()
