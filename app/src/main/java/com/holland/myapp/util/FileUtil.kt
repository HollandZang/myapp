package com.holland.myapp.util

import android.util.Log
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.util.zip.ZipInputStream

object FileUtil {
    fun upZipFile(source: String, target: String) {
        val destDir = File(target)
        val buffer = ByteArray(1024)
        val zis = ZipInputStream(FileInputStream(source))
        var zipEntry = zis.nextEntry
        while (zipEntry != null) {
            while (zipEntry != null) {
                val newFile = File(destDir, zipEntry.name)
                if (zipEntry.isDirectory) {
                    if (!newFile.isDirectory && !newFile.mkdirs()) {
                        throw IOException("Failed to create directory $newFile")
                    }
                    Log.d("FILE", "create dir: ${newFile.path}")
                } else {
                    // fix for Windows-created archives
                    val parent = newFile.parentFile
                    if (!parent!!.isDirectory && !parent.mkdirs()) {
                        throw IOException("Failed to create directory $parent")
                    }

                    // write file content
                    val fos = FileOutputStream(newFile)
                    var len: Int
                    while (zis.read(buffer).also { len = it } > 0) {
                        fos.write(buffer, 0, len)
                    }
                    fos.close()
                    Log.d("FILE", "create file: ${newFile.path}")
                }
                zipEntry = zis.nextEntry
            }
        }
        zis.closeEntry()
        zis.close()
    }
}