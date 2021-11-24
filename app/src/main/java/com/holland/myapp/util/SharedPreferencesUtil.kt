package com.holland.myapp.util

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.holland.myapp.entity.Version

object SharedPreferencesUtil {

    private const val SYSTEM = "system"
    private const val SYSTEM_VERSION = "version"

    @JvmStatic
    @JvmName("SharedPreferences")
    fun Context.saveVersion(version: Version) {
        this.getSharedPreferences(SYSTEM, AppCompatActivity.MODE_PRIVATE)
            .edit()
            .putString(SYSTEM_VERSION, GsonUtil.instance.toJson(version))
            .apply()
    }

    @JvmStatic
    @JvmName("SharedPreferences")
    fun Context.getVersion(): Version? {
        return GsonUtil.instance.fromJson(
            this.getSharedPreferences(SYSTEM, AppCompatActivity.MODE_PRIVATE)
                .getString(SYSTEM_VERSION, null), Version::class.java
        )
    }
}