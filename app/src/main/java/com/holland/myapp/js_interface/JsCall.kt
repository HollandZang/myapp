package com.holland.myapp.js_interface

import android.annotation.SuppressLint
import android.webkit.WebView

class JsCall(private val webView: WebView) {

    /**
     * @param type 1: 方法回调类型
     * @param args 2: 参数列表
     */
    @SuppressLint("ObsoleteSdkInt")
    fun appCallJs(type: Int, vararg args: String) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            webView.evaluateJavascript("appCallJs(${composeParameter(type, *args)});", null)
        } else {
            webView.loadUrl("javascript:appCallJs(${composeParameter(type, *args)});")
        }
    }

    private fun composeParameter(type: Int, vararg args: String): String {
        return if (args.isEmpty()) {
            "'$type'"
        } else {
            return "'$type',${args.map { "'$it'" }.reduce { acc, s -> "$acc,$s" }}"
        }
    }
}