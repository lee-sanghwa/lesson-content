package com.lysanghwa.lessoncontent.config

import com.lysanghwa.lessoncontent.common.interceptor.ControllerInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMVCConfig(
    val controllerInterceptor: ControllerInterceptor
) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(controllerInterceptor)
    }
}
