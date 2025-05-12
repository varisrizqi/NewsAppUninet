package com.tipiz.newsappuninet.uitls

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DateUtil {
    fun dateFormat(date: String?): String {
        return if (date.isNullOrEmpty()) ""
        else {
            val currentFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            val datePharse = currentFormat.parse(date)
            val toFormat = SimpleDateFormat("MMM,dd yyyy", Locale.getDefault())
            toFormat.format(datePharse as Date)
        }
    }

}