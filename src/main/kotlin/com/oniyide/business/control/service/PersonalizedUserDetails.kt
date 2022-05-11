package com.oniyide.business.control.service

import com.oniyide.business.control.repository.UserRepository
import com.oniyide.business.entity.UserAccount
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class PersonalizedUserDetails(private val userRepository: UserRepository) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val userAccount = userRepository.findByUsernameOrEmail(username, username)
        return userAccount?.toUser() ?: throw UsernameNotFoundException("invalid username for user")
    }
}

fun UserAccount.toUser(): User {
    return with(this) {
        User(username, password, emptyList<GrantedAuthority>())
    }
}