package com.lysanghwa.lessoncontent.service

import com.lysanghwa.lessoncontent.dto.LoginDto
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class UserService {

    private val logger = LoggerFactory.getLogger(UserService::class.java)

    fun login(params: LoginDto) {
    }
}
