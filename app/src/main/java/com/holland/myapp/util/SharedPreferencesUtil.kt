package com.holland.myapp.util

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.holland.myapp.po.Version

object SharedPreferencesUtil {

    private const val SYSTEM = "system"
    private const val SYSTEM_VERSION = "version"

    fun saveVersion(context: Context, version: Version) {
        context.getSharedPreferences(SYSTEM, AppCompatActivity.MODE_PRIVATE)
            .edit()
            .putString(SYSTEM_VERSION, GsonUtil.instance.toJson(version))
            .apply()
    }

    fun getVersion(context: Context): Version? {
        return GsonUtil.instance.fromJson(
            context.getSharedPreferences(SYSTEM, AppCompatActivity.MODE_PRIVATE)
                .getString(SYSTEM_VERSION, null), Version::class.java
        )
    }
}