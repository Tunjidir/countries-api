package com.oniyide.business.boundary

import com.oniyide.business.control.service.AuthService
import com.oniyide.business.entity.SignUpRequest
import com.oniyide.business.entity.UserAccount
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(private val authService: AuthService) {
  
  @PostMapping( "/signup")
  fun signup(@RequestBody signUpRequest: SignUpRequest) : ResponseEntity<UserAccount> {
    return ResponseEntity.ok(authService.registerUser(signUpRequest))
  }
}