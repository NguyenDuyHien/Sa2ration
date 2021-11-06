@file:SuppressLint("PrivateApi")
package com.xda.sa2ration.utils

import android.annotation.SuppressLint
import android.util.Log

// region Change saturation
fun getSaturationValue() =
    try {
        val systemProperties = Class.forName("android.os.SystemProperties")
        val get = systemProperties.getMethod("get", String::class.java)
        val saturationValue = get.invoke(null, "persist.sys.sf.color_saturation")?.toString()
        saturationValue?.toFloat() ?: 1.0f
    } catch (e: Exception) {
        Log.e("Sa2ration", e.stackTraceToString())
        1.0f
    }

fun setSaturationValue(value: Float) {
    sudo("setprop persist.sys.sf.color_saturation $value")
}

fun changeSaturationValue(value: Float) {
    sudo("service call SurfaceFlinger 1022 f $value")
}
// endregion

// region Enable Dci
fun getEnableDci() =
    try {
        val systemProperties = Class.forName("android.os.SystemProperties")
        val get = systemProperties.getMethod("get", String::class.java)
        val enableDci = get.invoke(null, "persist.sys.sf.native_mode")?.toString()
        enableDci?.toBoolean() ?: false
    } catch (e: Exception) {
        Log.e("Sa2ration", e.stackTraceToString())
        false
    }

fun setEnableDci(isEnable: Boolean) {
    sudo("service call SurfaceFlinger 1023 i32 " + if (isEnable) 0 else 1)
    sudo("setprop persist.sys.sf.native_mode " + if (isEnable) 0 else 1)
}
// endregion