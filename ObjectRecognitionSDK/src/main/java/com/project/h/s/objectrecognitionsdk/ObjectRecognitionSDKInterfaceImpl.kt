package com.project.h.s.objectrecognitionsdk

import android.content.Context
import android.content.Intent
import com.project.h.s.objectrecognitionsdk.presentation.signin.SignInActivity

class ObjectRecognitionSDKInterfaceImpl : ObjectRecognitionSDKInterface {
    override fun initialize(context: Context) {
        val intent = Intent()
        intent.setClass(context, SignInActivity::class.java)
        intent.setAction(SignInActivity::class.java.getName())
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
        context.startActivity(intent)
    }
}
