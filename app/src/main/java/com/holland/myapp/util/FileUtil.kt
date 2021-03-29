package com.holland.myapp.util

import android.util.Log
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.util.zip.ZipInputStream

object FileUtil {
    fun mkdir(path: String) {
        val file = File(path)
        when (file.exists()) {
            true -> println("exists directory: $path")
            false -> {
                file.mkdir()
                println("create directory: $path")
            }
        }
    }

    fun newLine2File(content: String?, path: String, fileName: String) {
        mkdir(path)
        val file = File("$path${File.separatorChar}$fileName")
        when (file.exists()) {
            true -> println("rebuild file: ${file.path}")
            false -> {
                println("create file: ${file.path}")
                file.createNewFile()
            }
        }
        content?.let { file.appendText(it + System.getProperty("line.separator")) }
    }

    fun newFile(content: String?, path: String, fileName: String) {
        mkdir(path)
        val file = File("$path${File.separatorChar}$fileName")
        when (file.exists()) {
            true -> println("rebuild file: ${file.path}")
            false -> {
                println("create file: ${file.path}")
                file.createNewFile()
            }
        }
        content?.let { file.writeText(it + System.getProperty("line.separator")) }
    }

    fun readFile(path: String, fileName: String): Array<String> {
        val file = File("$path${File.separatorChar}$fileName")
        return if (file.exists()) {
            file.readLines().toTypedArray()
        } else arrayOf()
    }

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