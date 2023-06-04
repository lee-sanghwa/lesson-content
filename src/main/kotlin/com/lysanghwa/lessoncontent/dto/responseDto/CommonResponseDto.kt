package com.lysanghwa.lessoncontent.dto.responseDto

import com.lysanghwa.lessoncontent.common.util.CommonUtils
import org.slf4j.MDC

data class CommonResponseDto(
    val code: ResponseCode,
    val message: String = ResponseCode.SUCCESS.toString(),
    val resultData: Any? = null
) {
    val status: Int = code.status
    val lshTraceUuid = MDC.get(CommonUtils.MDC_KEY_REQUEST_ID)
}
