package com.lysanghwa.lessoncontent.common.util

import org.springframework.stereotype.Component

@Component
class CommonUtils {

    companion object {
        val REQUEST_ID_HEADER_NAME = "lsh-trace-uuid"
        val MDC_KEY_REQUEST_ID = "lshTraceUuid"
        val MDC_KEY_SERVER_IP = "serverIp"
        val REQUEST_SERVER_NAME_HEADER_NAME = "lsh-server-name"
    }
}
