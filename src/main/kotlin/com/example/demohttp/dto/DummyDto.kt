package com.example.demohttp.dto

import java.io.Serializable

data class DummyDto(
    val username: String,
    val full_name: String,
    val gender: String,
    val birthdate: String,
    val location: String,
    val bio: String,
    val followsers: Int,
    val profile_picture: String,
    val websize: String,
    val interests: String
): Serializable