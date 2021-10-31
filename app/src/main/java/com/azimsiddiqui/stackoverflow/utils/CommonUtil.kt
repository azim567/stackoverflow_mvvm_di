package com.azimsiddiqui.stackoverflow.utils

import java.text.SimpleDateFormat

 fun getDateTime(time: Long): String? {
     return try {
         val sdf = SimpleDateFormat("dd-MM-yyyy")
         sdf.format(time * 1000L)
     } catch (e: Exception) {
         e.toString()
     }
}