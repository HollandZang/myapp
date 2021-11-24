package com.holland.myapp.js_interface

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.webkit.JavascriptInterface
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.holland.myapp.CameraActivity
import com.holland.myapp.common.ActivityResultCode
import com.holland.myapp.util.CameraUtil


class JsInterface(private val activity: Activity) {
    @JavascriptInterface
    fun onToast(msg: String?) = Toast.makeText(
        activity,
        if (msg == null || msg.isBlank()) "消息为空" else msg,
        Toast.LENGTH_SHORT
    ).show()

    @JavascriptInterface
    fun onCameraX() = activity.startActivityForResult(
        Intent(activity, CameraActivity::class.java),
        ActivityResultCode.REQUEST_TAKE_PHOTO_CAMERA_X.ordinal
    )

    @JavascriptInterface
    fun onCamera() =
        // Request camera permissions
        if (CameraActivity.REQUIRED_PERMISSIONS.all {
                ContextCompat.checkSelfPermission(
                    activity, it
                ) == PackageManager.PERMISSION_GRANTED
            }) {
            CameraUtil.openCamera(activity)
        } else {
            ActivityCompat.requestPermissions(
                activity,
                CameraActivity.REQUIRED_PERMISSIONS,
                CameraActivity.REQUEST_CODE_PERMISSIONS
            )
        }
}