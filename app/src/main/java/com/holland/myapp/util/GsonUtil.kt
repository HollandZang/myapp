package com.holland.myapp.util

import com.google.gson.Gson
import com.google.gson.GsonBuilder


object GsonUtil {
    val instance: Gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd HH:mm:ss")
        .create()
}
