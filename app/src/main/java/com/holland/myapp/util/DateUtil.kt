package com.holland.myapp.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateUtil {

    @JvmStatic
    @JvmName("LocalDate")
    fun LocalDate.toYYYYMMDD(): String = DateTimeFormatter.ofPattern("yyyyMMdd").format(this)

    @JvmStatic
    @JvmName("LocalDateTime")
    fun LocalDateTime.toStr(): String = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss").format(this)
}