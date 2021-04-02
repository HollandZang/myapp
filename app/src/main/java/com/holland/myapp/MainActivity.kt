package com.holland.myapp

import android.os.Bundle
import android.os.Environment
import android.view.KeyEvent
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.holland.myapp.js_interface.JsInterface
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val db = Room.databaseBuilder(
//            applicationContext,
//            UserDatabase::class.java, "user"
//        ).build()

        webView = findViewById<WebView>(R.id.web_view).apply {
            loadUrl("file:///android_asset/dist/index.html")
//            loadUrl("file:///${filesDir.path}/web/dist/index.html")/*/data/user/0/com.holland.myapp/files*/
            addJavascriptInterface(JsInterface(this@MainActivity, this), "android")
            settings.apply {
                javaScriptEnabled = true
                setSupportZoom(false)
                domStorageEnabled = true
            }
            this.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    url: String?
                ): Boolean {
                    view!!.loadUrl(url!!)
                    return true
                }
            }
        }
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            val imageBitmap = data?.extras?.get("data") as Bitmap
//            imageView.setImageBitmap(imageBitmap)
//        }
//    }

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

}