package com.holland.myapp.util

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.util.concurrent.TimeUnit

typealias OnResponse = (response: Response) -> Unit
typealias OnFailure = (exception: Exception) -> Unit

object HttpUtil {

    const val myServerHost = "119.23.68.6"
    const val myFileUrl = "http://$myServerHost:8763"

    fun get(
        context: Context,
        url: String,
        data: Map<String, *>?,
        onResponse: OnResponse?,
        onFailure: OnFailure?
    ) {
        val build = url.toHttpUrlOrNull()?.newBuilder().apply {
            data?.forEach { (t, u) -> this!!.addEncodedQueryParameter(t, u.toString()) }
        }!!.build()
        val request = Request.Builder()
            .url(build)
            .get()
            .build()
        BaseClient.baseRequestAsync(context, request, onResponse, onFailure)
    }

    fun postForm(
        context: Context,
        url: String,
        data: Map<String, *>?,
        onResponse: OnResponse?,
        onFailure: OnFailure?
    ) {
        val formBody = FormBody.Builder().let {
            data?.forEach { (t, u) -> it.addEncoded(t, u.toString()) }
            it.build()
        }
        val request = Request.Builder()
            .url(url)
            .post(formBody)
            .build()
        BaseClient.baseRequestAsync(context, request, onResponse, onFailure)
    }

    fun postJson(
        context: Context,
        url: String,
        data: Any?,
        onResponse: OnResponse?,
        onFailure: OnFailure?
    ) {
        val request = Request.Builder()
            .url(url)
            .post(GsonUtil.instance.toJson(data).toRequestBody())
            .build()
        BaseClient.baseRequestAsync(context, request, onResponse, onFailure)
    }
}

object BaseClient {
    private val INSTANCE = OkHttpClient.Builder()
        .connectTimeout(3, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .build()

    fun baseRequestAsync(
        context: Context,
        request: Request,
        onResponse: OnResponse?,
        onFailure: OnFailure?
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
}

abstract class BaseCallback(private val context: Context, private val url: String?) : Callback {
    override fun onResponse(call: Call, response: Response) {
        if (!response.isSuccessful) throw IOException("Unexpected code $response")
        Log.d(this.javaClass.name, "请求成功: $url")
    }

    override fun onFailure(call: Call, e: IOException) {
        Log.d(this.javaClass.name, "请求失败: $url")
        e.printStackTrace()
        Handler(Looper.getMainLooper()).run {
            post {
                Toast.makeText(context, "网络错误", Toast.LENGTH_SHORT).show()
            }
        }
    }
}