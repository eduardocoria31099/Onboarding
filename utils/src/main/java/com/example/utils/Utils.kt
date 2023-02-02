package com.example.utils

import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern


object Utils {

    fun isValidEmail(email: CharSequence): Boolean {
        val emailPattern = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
        )
        return emailPattern.matcher(email).matches()
    }

    fun convertLongToTime(time: Long): String {
        val date = Date(time)
        date.minutes = date.minutes + 480
        val format = SimpleDateFormat(
            "yyyy-MM-dd",
            Locale.getDefault()
        )
        return format.format(date)
    }
}