package com.oniyide.business.control.service

import com.oniyide.business.control.repository.UserRepository
import com.oniyide.business.entity.UserAccount
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class PersonalizedUserDetails(@Autowired private val userRepository: UserRepository) : UserDetailsService {
    
    override fun loadUserByUsername(username: String): UserDetails {
        val UserAccount = userRepository.findByUsernameOrEmail(username, username)
            .orElseThrow { UsernameNotFoundException("invalid username for user") }
        return UserAccount.toUser()
    }
}

fun UserAccount.toUser() : User {
    return with(this) {
        User(username, password, emptyList<GrantedAuthority>())
    }
}