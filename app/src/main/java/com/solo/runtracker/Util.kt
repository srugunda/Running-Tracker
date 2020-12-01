package com.solo.runtracker

import java.text.SimpleDateFormat
import java.util.*


fun getDate(time: Long): String? {
    val formatter = SimpleDateFormat("dd/MM/yyyy hh:mm a")
    val dateString: String = formatter.format(Date(time))
    return "" + dateString
}

