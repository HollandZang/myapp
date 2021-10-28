package com.holland.myapp

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.webkit.ConsoleMessage
import android.webkit.ConsoleMessage.MessageLevel
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider.getUriForFile
import androidx.core.view.isVisible
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.holland.myapp.common.ActivityResultCode
import com.holland.myapp.js_interface.JsCall
import com.holland.myapp.js_interface.JsInterface
import com.holland.myapp.util.CameraUtil
import com.holland.myapp.util.DateUtil.toStr
import com.holland.myapp.util.DateUtil.toYYYYMMDD
import com.holland.myapp.util.HttpUtil
import kotlinx.android.synthetic.main.activity_main.view.*
import java.io.File
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var fab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val db: UserDatabase = Room.databaseBuilder(
//            applicationContext,
//            UserDatabase::class.java, "user"
//        ).build()

        val logDoc = File("${externalCacheDir!!.parent!!}/log")
            .also { if (it.exists().not()) it.mkdir() }
        val crashDoc = File("${logDoc.path}/crash")
            .also { if (it.exists().not()) it.mkdir() }

        Thread.setDefaultUncaughtExceptionHandler { t, e ->
            logDoc.also { if (it.exists().not()) it.mkdir() }
            crashDoc.also { if (it.exists().not()) it.mkdir() }
            File(
                "${crashDoc.path}/${LocalDate.now().toYYYYMMDD()}.txt"
            )/*/storage/emulated/0/Android/data/com.holland.myapp/log*/
                .appendText(
                    "${
                        LocalDateTime.now().toStr()
                    }::${t.name}::${e.javaClass.name}::${e.cause?.message}\n${e.stackTraceToString()}"
                )
        }

        fab = findViewById(R.id.fab)
        //开启调试模式
        if (BuildConfig.DEBUG) {
            //悬浮球配置：查看日志
            fab.apply {
                setOnClickListener {
                    val file = File("${crashDoc.path}/${LocalDate.now().toYYYYMMDD()}.txt")

                    val intent = Intent()
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) //设置标记
                    intent.action = Intent.ACTION_VIEW //动作，查看
                    val fileUri: Uri =
                        if (Build.VERSION.SDK_INT >= 24) {
                            getUriForFile(
                                this@MainActivity,
                                "${BuildConfig.APPLICATION_ID}.fileprovider",
                                file
                            )
                        } else {
                            Uri.fromFile(file)
                        }
                    this@MainActivity.grantUriPermission(
                        BuildConfig.APPLICATION_ID,
                        fileUri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )
                    intent.setDataAndType(fileUri, "text/plain") //设置类型
                    context.startActivity(intent)
                }
            }
        } else {
            fab.isVisible = false
        }

        webView = findViewById<WebView>(R.id.web_view).apply {
            loadUrl("file:///android_asset/dist/index.html")
//            loadUrl("file:///${filesDir.path}/web/dist/index.html")   /*  /data/user/0/com.holland.myapp/files    */
            //暴露调用方法
            addJavascriptInterface(JsInterface(this@MainActivity), "\$App")
            settings.apply {
                //设置支持JS
                javaScriptEnabled = true
                //设置支持缩放
                setSupportZoom(false)
                //设置支持DomStorage
                domStorageEnabled = true
            }
            //设置为app打开，而非网页打开
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
            //打印h5 err日志
            this.webChromeClient = object : WebChromeClient() {
                override fun onConsoleMessage(consoleMessage: ConsoleMessage): Boolean {
                    when (consoleMessage.messageLevel()) {
                        MessageLevel.ERROR -> Log.e("H5", consoleMessage.message())
                    }
                    return super.onConsoleMessage(consoleMessage)
                }
            }
        }
    }

    //设置返回事件
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val jsCall = JsCall(webView)
        when (requestCode) {
            ActivityResultCode.REQUEST_TAKE_PHOTO_CAMERA.ordinal -> {
                jsCall.appCallJs(1, CameraUtil.currentPhotoPath)
            }
            else -> {
            }
        }
    }

    //设置返回键赋值给H5页面
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