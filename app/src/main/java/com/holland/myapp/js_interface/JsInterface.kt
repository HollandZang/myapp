package com.holland.myapp.js_interface

import android.app.Activity
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.widget.Toast


class JsInterface(private val activity: Activity, private val webView: WebView) {
    @JavascriptInterface
    fun showToast(msg: String?) {
        Log.d(this::class.java.name, "do function: showToast, info: $msg")
        Toast.makeText(activity, "安卓提示: $msg", Toast.LENGTH_SHORT).show()
    }
}