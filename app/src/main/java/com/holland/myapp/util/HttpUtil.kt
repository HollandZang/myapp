package com.holland.myapp.util

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import okhttp3.*
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONStringer
import java.io.IOException
import java.util.concurrent.TimeUnit

object HttpUtil {

    fun postJson(context: Context, data: Any) {
        val request = Request.Builder()
            .url("http://www.baidu.com")
            .post(JSONStringer().value(data).toString().toRequestBody())
            .build()
        BaseClient.baseRequestAsync(context, request, { println(it.body) }, { println(it.message) })
    }
}

object BaseClient {
    private val INSTANCE = OkHttpClient.Builder()
        .callTimeout(2, TimeUnit.SECONDS)
        .build()

    fun baseRequestAsync(
        context: Context,
        request: Request,
        onResponse: ((response: Response) -> Unit)?,
        onFailure: ((exception: Exception) -> Unit)?
    ) {
        INSTANCE.newCall(request).enqueue(
            object : BaseCallback(context, request.url.encodedPath) {
                override fun onResponse(call: Call, response: Response) {
                    super.onResponse(call, response)
                    onResponse?.invoke(response)
                }

                override fun onFailure(call: Call, e: IOException) {
                    super.onFailure(call, e)
                    onFailure?.invoke(e)
                }
            }
        )
    }

    fun toast(context: Context, msg: String) {
        Handler(Looper.getMainLooper()).run {
            post {
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            }
        }
    }
}

abstract class BaseCallback(private val context: Context, private val url: String?) : Callback {
    override fun onResponse(call: Call, response: Response) {
        if (!response.isSuccessful) throw IOException("Unexpected code $response")
        Log.d(this.javaClass.name, "请求成功: $url")
    }

    override fun onFailure(call: Call, e: IOException) {
        Log.d(this.javaClass.name, "请求失败: $url")
        e.printStackTrace()
        BaseClient.toast(context, "网络错误")
    }
}