package com.icedpath.appstart

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AppstartApplication

fun main(args: Array<String>) {
	runApplication<AppstartApplication>(*args)
}
