package com.lysanghwa.lessoncontent

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.EnableAspectJAutoProxy

@SpringBootApplication
@EnableAspectJAutoProxy
class LessonContentApplication

fun main(args: Array<String>) {
    runApplication<LessonContentApplication>(*args)
}
