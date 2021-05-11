package com.holland.myapp.js_interface

import android.app.Activity
import android.content.Intent
import android.webkit.JavascriptInterface
import android.widget.Toast
import com.holland.myapp.CameraActivity
import com.holland.myapp.util.CameraUtil


class JsInterface(private val activity: Activity) {
    @JavascriptInterface
    fun showToast(msg: String?) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
    }

    @JavascriptInterface
    fun cameraX() {
        return activity.startActivityForResult(
            Intent(activity, CameraActivity::class.java),
            CameraUtil.REQUEST_TAKE_PHOTO
        )
    }

    @JavascriptInterface
    fun camera() {
        CameraUtil.openCamera(activity)
        println("CAMERA::" + CameraUtil.currentPhotoPath)
    }

}