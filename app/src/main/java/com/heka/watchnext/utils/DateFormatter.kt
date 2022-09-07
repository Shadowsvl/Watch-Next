package com.heka.watchnext.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

object DateFormatter {

    fun formatDatePatterns(date: String, fromPattern: String, toPattern: String): String {
        if (fromPattern.isBlank() || toPattern.isBlank() || date.isBlank()) return date
        return try {
            val formatterFrom = DateTimeFormatter.ofPattern(fromPattern, Locale.getDefault())
            val formatterTo = DateTimeFormatter.ofPattern(toPattern, Locale.getDefault())
            val localDate = LocalDate.parse(date, formatterFrom)
            localDate.format(formatterTo)
        } catch (e: Exception) {
            date
        }
    }

}