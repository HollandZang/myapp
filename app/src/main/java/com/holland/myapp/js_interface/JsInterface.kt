package com.holland.myapp.js_interface

import android.app.Activity
import android.content.Intent
import android.webkit.JavascriptInterface
import android.widget.Toast
import com.holland.myapp.CameraActivity
import com.holland.myapp.common.ActivityResultCode
import com.holland.myapp.util.CameraUtil


class JsInterface(private val activity: Activity) {
    @JavascriptInterface
    fun onToast(msg: String?) {
        Toast.makeText(activity, msg ?: "消息为空", Toast.LENGTH_SHORT).show()
    }

    @JavascriptInterface
    fun onCameraX() {
        return activity.startActivityForResult(
            Intent(activity, CameraActivity::class.java),
            ActivityResultCode.REQUEST_TAKE_PHOTO_CAMERA_X.ordinal
        )
    }

    @JavascriptInterface
    fun onCamera() {
        CameraUtil.openCamera(activity)
    }

}