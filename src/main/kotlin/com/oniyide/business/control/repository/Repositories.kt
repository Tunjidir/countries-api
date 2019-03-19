package com.oniyide.business.control.repository

import com.oniyide.business.entity.Country
import com.oniyide.business.entity.UserAccount
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.Optional
import javax.transaction.Transactional

@Repository
interface UserRepository : JpaRepository<UserAccount, Long> {
  
  fun findByUsernameOrEmail(username: String, email: String) : Optional<UserAccount>
}

@Repository
interface CountryRepository : JpaRepository<Country, Long> {
  
  @Query("select c from Country c")
  fun getCountries() : List<Country>
}