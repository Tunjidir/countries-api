package com.oniyide.business.entity

import org.hibernate.annotations.NaturalId
import java.time.Instant
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@Entity
@Table(name = "user_table")
class UserAccount(
  
  @field:NotBlank
  @Column(name = "firstname", nullable = false)
  val firstName: String,
  
  @field:NotBlank
  @Column(name = "lastname", nullable = false)
  val lastName: String,
  
  @field:NotBlank
  @Column(name = "date_of_birth", nullable = false)
  val dateOfBirth: LocalDateTime,
  
  @field:NotBlank
  @Column(name = "email", nullable = false)
  @Email
  val email: String,
  
  @field:NotBlank
  @Column(name = "username", nullable = false)
  val username: String,
  
  @field:NotBlank
  @Column(name = "password", nullable = false)
  var password: String,

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long = 0

) {
  
  override fun equals(other: Any?) : Boolean {
    if(this === other) return true
    if(javaClass != other?.javaClass) return false
    
    other as UserAccount
    
    if(firstName != other.firstName) return false
    if(lastName != other.lastName) return false
    if(dateOfBirth != other.dateOfBirth) return false
    if(email != other.email) return false
    if(username != other.username) return false
    if(password != other.password) return false
    if(id != other.id) return false
    
    return true
  }
  
  override fun hashCode(): Int {
    var result = firstName.hashCode()
    result = 31 * result + lastName.hashCode()
    result = 31 * result + dateOfBirth.hashCode()
    result = 31 * result  + email.hashCode()
    result = 31 * result + username.hashCode()
    result = 31 * result + password.hashCode()
    result = 31 * result + id.hashCode()
    
    return result
  }

}

@Entity
@Table(name = "countries_table")
class Country (
  
  @field:NotBlank
  @Column(name = "name", nullable = false)
  @NaturalId
  var name: String,
  
  @field:NotBlank
  @field:Size(max = 40)
  @Column(name = "continent", nullable = false)
  var continent: String,
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long = 0

) {
  
  @field:NotBlank
  val created: Instant = Instant.now()
  
  override fun equals(other: Any?) : Boolean {
    if(this === other) return true
    if(this.javaClass != other?.javaClass) return false
    
    other as Country
    if(name != other.name) return false
    if(continent != other.continent) return false
    if(id != other.id) return false
    if(created != other.created) return false
    return true
  }
  
  override fun hashCode(): Int {
    var result = id.hashCode()
    result = 31 * result + name.hashCode()
    result = 31 * result + continent.hashCode()
    
    return result
  }
}