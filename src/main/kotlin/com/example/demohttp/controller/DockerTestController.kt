package com.example.demohttp.controller

import com.example.demohttp.service.DockerTestService
import org.springframework.stereotype.Controller
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/redis")
@Validated
class DockerTestController(private val dockerTestService: DockerTestService) {

    @PostMapping
    fun addText(@RequestBody text: String) {
        dockerTestService.addText(text)
    }

    @GetMapping
    fun retriveAllUser() : String {
        return dockerTestService.getText()
    }
}