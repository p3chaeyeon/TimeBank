package kookmin.software.capstone2023.timebank.domain.repository

import kookmin.software.capstone2023.timebank.domain.model.BankBranch
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BankBranchJpaRepository : JpaRepository<BankBranch, Long>
