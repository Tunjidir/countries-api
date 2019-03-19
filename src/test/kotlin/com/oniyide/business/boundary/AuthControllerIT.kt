package com.oniyide.business.boundary

import com.oniyide.business.control.repository.UserRepository
import com.oniyide.business.entity.SignUpRequest
import com.oniyide.util.toJsonString
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDateTime

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class AuthControllerIT() {
    
    @Autowired
    private lateinit var mockMvc: MockMvc
    
    @Autowired
    private lateinit var userRepository: UserRepository
    
    @Autowired
    private lateinit var bCryptPasswordEncoder: BCryptPasswordEncoder
    
    @Test
    fun `signup should register user`() {
        val signup = SignUpRequest("olatunji", "oniyide", LocalDateTime.now(), "olatunji4you@gmail.com", "Tunjidir", "Billy")
        mockMvc.perform(
            post("/signup")
                .content(signup.toJsonString())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
        ).andExpect(status().isOk)
    }
}