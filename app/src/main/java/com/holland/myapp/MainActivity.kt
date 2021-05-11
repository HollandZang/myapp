package com.holland.myapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.holland.myapp.js_interface.JsInterface
import com.holland.myapp.util.HttpUtil
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val db: UserDatabase = Room.databaseBuilder(
//            applicationContext,
//            UserDatabase::class.java, "user"
//        ).build()

        webView = findViewById<WebView>(R.id.web_view).apply {
            loadUrl("file:///android_asset/dist/index.html")
//            loadUrl("file:///${filesDir.path}/web/dist/index.html")   /*  /data/user/0/com.holland.myapp/files    */
            addJavascriptInterface(JsInterface(this@MainActivity), "App")
            settings.apply {
                javaScriptEnabled = true
                setSupportZoom(false)
                domStorageEnabled = true
            }
            this.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    Log.d("shouldOverrideUrlLoading", "url: $url")
                    if (Uri.parse(url).host != HttpUtil.myServerHost) {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        context.startActivity(intent)
                        return true
                    }
                    return false
                }
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack()
                return true
            } else {
                exitProcess(0)
            }
        }
        return super.onKeyDown(keyCode, event)
    }

}