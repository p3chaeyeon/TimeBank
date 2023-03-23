package kookmin.software.capstone2023.timebank.domain.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import kookmin.software.capstone2023.timebank.domain.model.Inquiry
import java.util.Optional

@Repository
interface InquiryRepository : JpaRepository<Inquiry, Long>{
}
