package com.lysanghwa.lessoncontent.common.exception

import com.lysanghwa.lessoncontent.dto.responseDto.ResponseCode

data class CustomException(
    val statusCode: Int,
    val code: ResponseCode,
    override val message: String = code.toString()
) : Exception()
