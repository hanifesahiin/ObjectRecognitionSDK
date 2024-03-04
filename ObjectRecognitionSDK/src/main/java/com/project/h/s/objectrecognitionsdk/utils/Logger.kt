package com.project.h.s.objectrecognitionsdk.utils

import android.util.Log
import java.lang.Exception

object Logger {
    val Logger = "Logger"
    fun e(objectName: String, methodName: String, exception: Exception) {
        Log.e(
            Logger,
            "SDK $objectName Class \nmethod: $methodName \nexception: ${exception.cause} \nmessage: ${exception.message}"
        )
    }
    fun e(objectName: String, methodName: String, message: String) {
        Log.e(
            Logger,
            "SDK $objectName Class \nmethod: $methodName \nmessage: ${message}"
        )
    }
}