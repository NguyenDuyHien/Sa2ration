package com.xda.sa2ration.utils

import android.util.Log
import java.io.*
import java.lang.Exception

fun sudo(vararg strings: String) {
    try {
        val su = Runtime.getRuntime().exec("su")
        val outputStream = DataOutputStream(su.outputStream)
        for (s in strings) {
            outputStream.writeBytes(
                """
                    $s
                    
                    """.trimIndent()
            )
            outputStream.flush()
        }
        outputStream.writeBytes("exit\n")
        outputStream.flush()
        try {
            su.waitFor()
        } catch (e: InterruptedException) {
            Log.e("Sa2ration No Root?", e.stackTraceToString())
        }
        outputStream.close()
    } catch (e: Exception) {
        Log.d("Sa2ration", e.stackTraceToString())
    }
}

fun checkSudo(): Boolean {
    var st: StackTraceElement? = null

    try {
        val su = Runtime.getRuntime().exec("su")
        val outputStream = DataOutputStream(su.outputStream)
        outputStream.writeBytes("exit\n")
        outputStream.flush()
        val inputStream = DataInputStream(su.inputStream)
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        while (bufferedReader.readLine() != null) {
            bufferedReader.readLine()
        }
        su.waitFor()
    } catch (e: Exception) {
        Log.d("Sa2ration", e.stackTraceToString())
        for (s in e.stackTrace) {
            st = s
            if (st != null) break
        }
    }
    return st == null
}