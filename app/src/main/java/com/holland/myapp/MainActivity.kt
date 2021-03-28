package com.holland.myapp

import android.os.Bundle
import android.view.KeyEvent
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.holland.myapp.js_interface.JsInterface
import com.holland.myapp.repo.UserDatabase
import com.holland.myapp.util.FileUtil
import com.holland.myapp.util.HttpUtil
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity() {

    lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = Room.databaseBuilder(
            applicationContext,
            UserDatabase::class.java, "user"
        ).build()

        updateVersion()

        webView = findViewById<WebView>(R.id.web_view).apply {
//            loadUrl("file:///android_asset/dist/index.html")
            loadUrl("file:///${filesDir.path}/web/dist/index.html")
            addJavascriptInterface(JsInterface(this@MainActivity, this), "\$App")
            settings.apply {
                javaScriptEnabled = true
                setSupportZoom(false)
            }
            this.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    view!!.loadUrl(url!!)
                    return true
                }
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();
                return true;
            } else {
                exitProcess(0);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private fun updateVersion() {
        HttpUtil.postJson(this, "test")
        /*解压H5文件*/
        FileUtil.upZipFile("${filesDir.path}/web/dist.zip", "${filesDir.path}/web")
    }
}