package com.icedpath.appstart.controller.dto

import lombok.Builder
import lombok.Data

@Data
@Builder(toBuilder = true)
class UserDto(s: String, s1: String, s2: String) {
    var name: String? = null
    var email: String? = null
    var password: String

    init {
        name = s
        email = s1
        password = s2
    }
}