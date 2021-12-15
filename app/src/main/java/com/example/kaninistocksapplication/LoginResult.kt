package com.example.kaninistocksapplication

data class LoginResult(
    var id: Int = 0,
    var token: String = "",
    var email: String = "",
    var memberSince: Long = 0,
    var password: String? = null
)
