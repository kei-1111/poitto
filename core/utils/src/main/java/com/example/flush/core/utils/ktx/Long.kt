package com.example.flush.core.utils.ktx

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun Long.toFormattedTime(pattern: String = "yyyy年MM月dd日HH:mm"): String {
    val formatter = DateTimeFormatter.ofPattern(pattern).withZone(ZoneId.systemDefault())
    return formatter.format(Instant.ofEpochMilli(this))
}
