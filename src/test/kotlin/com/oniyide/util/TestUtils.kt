package com.oniyide.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

val objectMapper: ObjectMapper
    get() {
        return jacksonObjectMapper().apply {
            registerModule(JavaTimeModule())
            configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        }
    }

fun <T> T.toJsonString(): String {
    return objectMapper.writeValueAsString(this)
}

const val ENCRYPTED_PASSWORD = "#AKJfkfkjdcvkjaidHHFkfhajfajfhaRIHAIAK345kahfanvahaFFFFd"