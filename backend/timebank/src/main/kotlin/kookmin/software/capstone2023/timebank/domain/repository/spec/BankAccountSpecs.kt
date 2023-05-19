package kookmin.software.capstone2023.timebank.domain.repository.spec

import jakarta.persistence.criteria.JoinType
import jakarta.persistence.criteria.Predicate
import kookmin.software.capstone2023.timebank.domain.model.Account
import kookmin.software.capstone2023.timebank.domain.model.BankAccount
import kookmin.software.capstone2023.timebank.domain.model.User
import org.springframework.data.jpa.domain.Specification
import java.time.LocalDate

object BankAccountSpecs {
    fun withAccountNumber(
        accountNumber: String,
    ): Specification<BankAccount> {
        return Specification { root, _, criteriaBuilder ->
            criteriaBuilder.equal(root.get<String>("accountNumber"), accountNumber)
        }
    }

    fun withUser(
        id: Long?,
        name: String?,
        phoneNumber: String?,
        birthday: LocalDate?,
    ): Specification<BankAccount> {
        if (id == null && name == null && phoneNumber == null && birthday == null) {
            return Specification { _, _, _ -> null }
        }

        return Specification { root, query, criteriaBuilder ->
            query.distinct(true)

            val predicates = mutableListOf<Predicate>()

            val accountJoin = root.join<BankAccount, Account>("account", JoinType.INNER)
            val userJoin = accountJoin.join<Account, User>("users", JoinType.INNER)

            id?.let { predicates.add(criteriaBuilder.equal(userJoin.get<Long>("id"), it)) }
            name?.let { predicates.add(criteriaBuilder.like(userJoin.get("name"), "%$it%")) }
            phoneNumber?.let { predicates.add(criteriaBuilder.equal(userJoin.get<String>("phoneNumber"), it)) }
            birthday?.let { predicates.add(criteriaBuilder.equal(userJoin.get<LocalDate>("birthday"), it)) }

            criteriaBuilder.and(*predicates.toTypedArray())
        }
    }
}
