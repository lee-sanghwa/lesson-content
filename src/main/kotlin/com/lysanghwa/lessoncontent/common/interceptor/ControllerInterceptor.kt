package com.lysanghwa.lessoncontent.common.interceptor

import com.lysanghwa.lessoncontent.common.util.CommonUtils
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.util.ContentCachingRequestWrapper
import org.springframework.web.util.ContentCachingResponseWrapper
import java.net.InetAddress
import java.util.UUID

@Component
class ControllerInterceptor() : HandlerInterceptor {

    private var logger = LoggerFactory.getLogger(ControllerInterceptor::class.java)

    private fun getRequestLogCommonContent(request: HttpServletRequest): String {
        val queryString = if (request.queryString == null) "" else "?${request.queryString}"
        val headers = request.headerNames.toList().fold(mutableMapOf<String, Any>()) { acc, headerName ->
            acc[headerName] = request.getHeaders(headerName).toList()
            acc
        }

        val requestLogCommonContent = "${request.method.uppercase()} ${request.requestURL.toString() + queryString} header = $headers"

        return requestLogCommonContent
    }

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val lshTraceUuid = request.getHeader(CommonUtils.REQUEST_ID_HEADER_NAME) ?: UUID.randomUUID().toString()
        MDC.put(CommonUtils.MDC_KEY_REQUEST_ID, lshTraceUuid)

        val serverIp = InetAddress.getLocalHost().hostAddress
        MDC.put(CommonUtils.MDC_KEY_SERVER_IP, serverIp)

        val requestLogCommonContent = getRequestLogCommonContent(request)
        logger.info("REQUEST_PRE_HANDLE = $requestLogCommonContent")
        return true
    }

    override fun afterCompletion(request: HttpServletRequest, response: HttpServletResponse, handler: Any, ex: Exception?) {
        val wrappedRequest = request as ContentCachingRequestWrapper
        val wrappedResponse = response as ContentCachingResponseWrapper

        val requestLogCommonContent = getRequestLogCommonContent(request)

        val requestBody = String(wrappedRequest.contentAsByteArray)
        logger.info("REQUEST = $requestLogCommonContent body = $requestBody")

        val responseBody = String(wrappedResponse.contentAsByteArray)
        logger.info("RESPONSE = status-code: ${wrappedResponse.status}, body = $responseBody")

        MDC.remove(CommonUtils.MDC_KEY_REQUEST_ID)
        MDC.remove(CommonUtils.MDC_KEY_SERVER_IP)
    }
}
