package com.oniyide.business.boundary

import com.nhaarman.mockito_kotlin.doThrow
import com.nhaarman.mockito_kotlin.given
import com.oniyide.business.control.service.AuthService
import com.oniyide.business.control.service.PersonalizedUserDetails
import com.oniyide.business.entity.LoginRequest
import com.oniyide.business.entity.SignUpRequest
import com.oniyide.business.entity.toUser
import com.oniyide.util.toJsonString
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDateTime

@ExtendWith(SpringExtension::class)
@WebMvcTest(value = [AuthController::class])
class AuthControllerTest(@Autowired private val mockMvc: MockMvc, @Autowired private val passwordEncoder: BCryptPasswordEncoder) {
  
  @MockBean
  private lateinit var authService: AuthService
  
  @MockBean
  private lateinit var userDetails: PersonalizedUserDetails
  
  @Test
  fun `signup request should return a signed up user`() {
    val signUpRequest = SignUpRequest("olatunji", "oniyide", LocalDateTime.now(), "olatunji4you@gmail.com", "Tunjidir", "Billy")
    val user  = signUpRequest.toUser()
    given(authService.registerUser(signUpRequest)).willReturn(user)
    
    mockMvc.perform(
      post("/signup")
        .content(signUpRequest.toJsonString())
        .contentType(MediaType.APPLICATION_JSON_UTF8)
    ).andExpect(status().isOk)
      .andExpect(jsonPath("\$.firstName").value("olatunji"))
      .andExpect(jsonPath("\$.lastName").value("oniyide"))
  }
  
  @Test
  fun `login should log in valid user`() {
    val signUpRequest = SignUpRequest("olatunji", "oniyide", LocalDateTime.now(), "olatunji4you@gmail.com", "Tunjidir", "Billy")
    val user  = signUpRequest.toUser()
    given(authService.registerUser(signUpRequest)).willReturn(user)
  
    mockMvc.perform(
      post("/signup")
        .content(signUpRequest.toJsonString())
        .contentType(MediaType.APPLICATION_JSON_UTF8)
    ).andExpect(status().isOk)
    
    val login = LoginRequest("Tunjidir", "billy")
    given(userDetails.loadUserByUsername("Tunjidir")).willReturn(User("Tunjidir", passwordEncoder.encode("billy"), emptyList<GrantedAuthority>()))
    
    mockMvc.perform(
      post("/login")
        .content(login.toJsonString())
        .contentType(MediaType.APPLICATION_JSON_UTF8)
    ).andExpect(status().isOk)
      .andExpect(header().exists("Authorization"))
  }
  
  @Test
  fun `invalid details should not login`() {
    val loginRequest = LoginRequest("invalid", "invalid")
    given(userDetails.loadUserByUsername("invalid")).willThrow(UsernameNotFoundException::class.java)
    
    mockMvc.perform(
      post("/login")
        .content(loginRequest.toJsonString())
        .contentType(MediaType.APPLICATION_JSON_UTF8)
    ).andExpect(status().isUnauthorized)
      .andExpect(header().doesNotExist("Authorization"))
  }
}