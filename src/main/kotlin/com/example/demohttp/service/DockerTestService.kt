package com.example.demohttp.service

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class DockerTestService(private val redisTemplate: RedisTemplate<Any, Any>) {

    fun addText(text: String) {
        redisTemplate.opsForValue().set("docker", text)
    }

    fun getText(): String {
        return redisTemplate.opsForValue().get("docker") as String
    }
}