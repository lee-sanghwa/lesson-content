package com.lysanghwa.lessoncontent.common.aop

import com.lysanghwa.lessoncontent.common.exception.CustomException
import com.lysanghwa.lessoncontent.dto.responseDto.CommonResponseDto
import com.lysanghwa.lessoncontent.dto.responseDto.ResponseCode
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.FieldError
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.NoHandlerFoundException

@RestControllerAdvice
class CustomExceptionAdvice {
    private val logger = LoggerFactory.getLogger(CustomExceptionAdvice::class.java)

    @ExceptionHandler(CustomException::class)
    fun customException(ex: CustomException, request: HttpServletRequest) =
        ResponseEntity
            .status(ex.statusCode)
            .body(
                CommonResponseDto(
                    code = ex.code,
                    message = ex.message
                )
            )

    @ExceptionHandler(MissingServletRequestParameterException::class)
    fun handleMissingServletRequestParameterException(
        ex: MissingServletRequestParameterException
    ): ResponseEntity<CommonResponseDto> {
        logger.debug(ex.message)

        return ResponseEntity
            .status(400)
            .body(
                CommonResponseDto(
                    code = ResponseCode.INVALID_REQUEST_PARAM,
                    message = "${ex.parameterName} 는/은 필수 값입니다."
                )
            )
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(
        ex: HttpMessageNotReadableException
    ): ResponseEntity<CommonResponseDto> {
        logger.debug(ex.message)

        return ResponseEntity
            .status(400)
            .body(
                CommonResponseDto(
                    code = ResponseCode.INVALID_REQUEST_BODY,
                    message = "request body를 확인해주세요."
                )
            )
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(
        ex: MethodArgumentNotValidException
    ): ResponseEntity<CommonResponseDto> {
        val errorMessage = ex.bindingResult.allErrors.joinToString("그리고, ") {
                error ->
            "${(error as FieldError).field} 는/은 ${error.defaultMessage}"
        }

        return ResponseEntity
            .status(400)
            .body(
                CommonResponseDto(
                    code = ResponseCode.INVALID_REQUEST_BODY,
                    message = errorMessage
                )
            )
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun handleHttpRequestMethodNotSupportedException(
        ex: HttpRequestMethodNotSupportedException
    ): ResponseEntity<CommonResponseDto> {
        return ResponseEntity
            .status(404)
            .body(
                CommonResponseDto(
                    code = ResponseCode.NOT_SUPPORTED_HTTP_METHOD,
                    message = "${ex.method} 는/은 지원하지 않습니다."
                )
            )
    }

    @ExceptionHandler(NoHandlerFoundException::class)
    fun handleNoHandlerFoundException(
        ex: NoHandlerFoundException
    ): ResponseEntity<CommonResponseDto> {
        return ResponseEntity
            .status(404)
            .body(
                CommonResponseDto(
                    code = ResponseCode.NOT_SUPPORTED_HTTP_ENDPOINT,
                    message = "${ex.requestURL} 는/은 지원하지 않습니다."
                )
            )
    }

    @ExceptionHandler(Exception::class)
    fun handleException(
        ex: Exception
    ): ResponseEntity<CommonResponseDto> {
        logger.debug(ex.message)

        return ResponseEntity
            .status(500)
            .body(
                CommonResponseDto(
                    code = ResponseCode.INTERNAL_SERVER_ERROR,
                    message = "내부 오류가 발생했습니다."
                )
            )
    }
}
