package com.oniyide.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.algorithms.Algorithm.HMAC512
import com.fasterxml.jackson.databind.ObjectMapper
import com.oniyide.business.entity.LoginRequest
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.IOException
import java.util.Date
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JWTAuthenticationFilter (
  private val authManager: AuthenticationManager
) : UsernamePasswordAuthenticationFilter() {
  
  init {
    authenticationManager = authManager
  }
  
  @Throws(AuthenticationException::class, IOException::class, ServletException::class)
  override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
    try {
      val credentials = ObjectMapper().readValue(request.inputStream, LoginRequest::class.java)
      return authManager.authenticate(
        UsernamePasswordAuthenticationToken(
          credentials.username, credentials.password, emptyList<GrantedAuthority>()
        )
      )
    } catch (ex: IOException) {
      throw RuntimeException(ex)
    }
  }
  
  override fun successfulAuthentication(
    request: HttpServletRequest,
    response: HttpServletResponse,
    chain: FilterChain,
    auth: Authentication
  ) {
    val token = JWT.create()
      .withSubject((auth.principal as User).username)
      .withExpiresAt(Date(System.currentTimeMillis() + EXPIRATION_TIME))
      .sign(Algorithm.HMAC512(SECRET.toByteArray()))
    
    response.addHeader(HEADER_STRING, TOKEN_PREFIX + token)
  }
}