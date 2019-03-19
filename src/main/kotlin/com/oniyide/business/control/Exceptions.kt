package com.oniyide.business.control

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NO_CONTENT)
class ResourceNotFoundException(message: String) : RuntimeException(message) {
  constructor(
    resourceName: String,
    fieldName: String,
    fieldValue: String?
    
  ) : this(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue))
}

@ResponseStatus(HttpStatus.UNAUTHORIZED)
class UnauthorizedException(message: String) : RuntimeException(message)