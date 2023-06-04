package com.lysanghwa.lessoncontent.common.aop

import com.fasterxml.jackson.databind.ObjectMapper
import com.lysanghwa.lessoncontent.dto.responseDto.CommonResponseDto
import com.lysanghwa.lessoncontent.dto.responseDto.ResponseCode
import org.springframework.core.MethodParameter
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice
import java.io.ByteArrayInputStream

@RestControllerAdvice
class ResponseAdvice : ResponseBodyAdvice<Any> {

    /** 특정 경우에만 적용되기를 원한다면 supports() 함수를 수정해주세요. */
    override fun supports(returnType: MethodParameter, converterType: Class<out HttpMessageConverter<*>>): Boolean {
        return true
    }

    override fun beforeBodyWrite(
        body: Any?,
        returnType: MethodParameter,
        selectedContentType: MediaType,
        selectedConverterType: Class<out HttpMessageConverter<*>>,
        request: ServerHttpRequest,
        response: ServerHttpResponse
    ): Any? {
        if (body == null) {
            return null
        }

        val mapper = ObjectMapper()
        val responseBodyString = mapper.writeValueAsString(body)
        val inputStream = ByteArrayInputStream(responseBodyString.toByteArray())
        val responseBodyObject = mapper.readValue(inputStream.bufferedReader().readText(), Object::class.java)

        var wrappedResponseBody: Any = responseBodyObject
        if (responseBodyObject is List<*> || responseBodyObject is Array<*>) {
            wrappedResponseBody = CommonResponseDto(code = ResponseCode.SUCCESS, resultData = responseBodyObject)
        } else if (responseBodyObject is MutableMap<*, *>) {
            val needWrapping = !(
                (responseBodyObject.containsKey("code") && ResponseCode.values().any { it.name == responseBodyObject["code"] }) &&
                    (responseBodyObject.containsKey("message") && responseBodyObject["message"] is String) &&
                    (responseBodyObject.containsKey("status") && ResponseCode.values().any { it.status == responseBodyObject["status"] })
                )

            if (needWrapping) {
                wrappedResponseBody = CommonResponseDto(code = ResponseCode.SUCCESS, resultData = responseBodyObject)
            }
        }

        return wrappedResponseBody
    }
}
