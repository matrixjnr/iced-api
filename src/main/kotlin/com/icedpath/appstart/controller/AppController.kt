package com.icedpath.appstart.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AppController {

    @GetMapping("/", produces = ["application/json"])
    fun index(): HashMap<String, String> {
        val message = HashMap<String, String>()
        message["name"] = "Iced APIs"
        message["version"] = "1.0.0"
        return message
    }
}