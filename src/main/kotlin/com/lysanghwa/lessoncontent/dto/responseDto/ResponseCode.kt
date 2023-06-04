package com.lysanghwa.lessoncontent.dto.responseDto

enum class ResponseCode(val status: Int) {
    SUCCESS(status = 9999),

    RESOURCE_NOT_FOUND(-2001),
    NOT_SUPPORTED_HTTP_METHOD(-4001),
    NOT_SUPPORTED_HTTP_ENDPOINT(-4002),
    EXTERNAL_API_ERROR(-8500),
    INVALID_REQUEST_PARAM(status = -9998),
    INVALID_REQUEST_BODY(status = -9998),
    INTERNAL_SERVER_ERROR(status = -9999)
}
