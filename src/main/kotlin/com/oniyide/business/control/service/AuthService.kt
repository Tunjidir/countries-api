package com.oniyide.business.control.service

import com.oniyide.business.control.repository.UserRepository
import com.oniyide.business.entity.SignUpRequest
import com.oniyide.business.entity.UserAccount
import com.oniyide.business.entity.toUser
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

interface AuthService {
  
  fun registerUser(signUpRequest: SignUpRequest) : UserAccount
}

@Service
class AuthServiceImpl(
  private val userRepository: UserRepository,
  private val encoderPassword: BCryptPasswordEncoder
) : AuthService {
  
  override fun registerUser(signUpRequest: SignUpRequest): UserAccount {
    val encodedPassword = encoderPassword.encode(signUpRequest.password)
    signUpRequest.password = encodedPassword
    val userAccount = signUpRequest.toUser()
    return userRepository.save(userAccount)
  }
}

