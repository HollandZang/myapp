package com.holland.myapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.holland.myapp.po.Version
import com.holland.myapp.util.FileUtil
import com.holland.myapp.util.GsonUtil
import com.holland.myapp.util.HttpUtil
import com.holland.myapp.util.SharedPreferencesUtil
import java.io.File

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_main)

        updateVersion()
    }

    private fun updateVersion() {
        HttpUtil.get(
            this,
            "http://${HttpUtil.myServerHost}:8763/filesystem/download/android/web/version",
            null,
            {
                val serverVersion =
                    GsonUtil.instance.fromJson(it.body?.string(), Version::class.java)
                val currentVersion = SharedPreferencesUtil.getVersion(this)
                if (null == currentVersion || serverVersion.updateTime!! > currentVersion.updateTime) {
                    FileUtil.mkdir("${filesDir.path}/web")
                    HttpUtil.get(
                        this,
                        "http://${HttpUtil.myServerHost}:8763/filesystem/download/android/web",
                        mapOf(Pair("objectId", serverVersion.objectId)) as Map<String, *>,
                        {
                            val file = File("${filesDir.path}/web/dist.zip")
                            file.outputStream().run {
                                write(it.body?.bytes())
                            }
                            /*解压H5文件*/
                            FileUtil.upZipFile(
                                "${filesDir.path}/web/dist.zip",
                                "${filesDir.path}/web"
                            )
                            SharedPreferencesUtil.saveVersion(this, serverVersion)

                            runOnUiThread {
                                this.startActivity(Intent(this, MainActivity::class.java))
                                finish()
                            }
                        },
                        null
                    )
                } else {
                    runOnUiThread {
                        this.startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                }
            },
            null
        )
    }
}