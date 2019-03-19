package com.oniyide.business.control.service

import com.oniyide.business.control.repository.UserRepository
import com.oniyide.business.entity.SignUpRequest
import com.oniyide.business.entity.UserAccount
import com.oniyide.business.entity.toUser
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

interface AuthService : UserDetailsService {
  
  fun registerUser(signUpRequest: SignUpRequest) : UserAccount
}

@Service
class AuthServiceImpl(
  private val userRepository: UserRepository,
  private val encoderPassword: BCryptPasswordEncoder
) : AuthService {
  
  override fun loadUserByUsername(username: String): UserDetails {
    val UserAccount = userRepository.findByUsernameOrEmail(username, username)
      .orElseThrow { UsernameNotFoundException("invalid username for user") }
    return UserAccount.toUser()
  }
  
  override fun registerUser(signUpRequest: SignUpRequest): UserAccount {
    val encodedPassword = encoderPassword.encode(signUpRequest.password)
    signUpRequest.password = encodedPassword
    val userAccount = signUpRequest.toUser()
    return userRepository.save(userAccount)
  }
}

fun UserAccount.toUser() : User {
  return with(this) {
    User(username, password, emptyList<GrantedAuthority>())
  }
}

