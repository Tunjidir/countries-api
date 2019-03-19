package com.oniyide.business.entity

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime
import java.util.Date
import javax.validation.constraints.NotBlank

data class SignUpRequest(
  @field:NotBlank
  val firstName: String,
  
  @field:NotBlank
  val lastName: String,
  
  @field:NotBlank
  val dateOfBirth: LocalDateTime,
  
  @field:NotBlank
  val email: String,
  
  @field:NotBlank
  val username: String,
  
  @field:NotBlank
  var password: String
)

data class AddCountryRequest(
  @field:NotBlank
  val name: String,
  
  @field:NotBlank
  val continent: String
)

data class LoginRequest(
  @field:NotBlank
  @field:JsonProperty("username_or_email")
  var username: String? = null,
  
  @field:NotBlank
  var password: String? = null
)

fun AddCountryRequest.toCountry() : Country {
  return with(this) {
    Country(name, continent)
  }
}

fun SignUpRequest.toUser() : UserAccount {
  return with(this) {
    UserAccount(firstName, lastName, dateOfBirth, email, username, password)
  }
}


