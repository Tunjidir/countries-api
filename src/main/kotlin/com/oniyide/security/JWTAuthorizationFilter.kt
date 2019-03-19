package com.oniyide.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JWTAuthorizationFilter(private val authManager: AuthenticationManager)
  : BasicAuthenticationFilter(authManager) {
  override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
    val header = request.getHeader(HEADER_STRING)
    if (header == null || !header.startsWith(TOKEN_PREFIX)) {
      chain.doFilter(request, response)
      return
    }
    val authentication = getAuthentication(request)
    SecurityContextHolder.getContext().authentication = authentication
    chain.doFilter(request, response)
  }
  
  fun getAuthentication(request: HttpServletRequest): UsernamePasswordAuthenticationToken? {
    val token = request.getHeader(HEADER_STRING)
    if (token != null) {
      val user = JWT.require(Algorithm.HMAC512(SECRET.toByteArray()))
        .build()
        .verify(token.replace(TOKEN_PREFIX, ""))
        .subject
      
      if (user != null) {
        return UsernamePasswordAuthenticationToken(user, null, arrayListOf())
      }
      return null
    }
    return null
  }
}