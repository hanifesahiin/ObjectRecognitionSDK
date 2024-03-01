package com.project.h.s.objectrecognitionsdk

import android.content.Context
import android.content.Intent
import com.project.h.s.objectrecognitionsdk.data.UserSharedPreference
import com.project.h.s.objectrecognitionsdk.presentation.signin.SignInActivity
import com.project.h.s.objectrecognitionsdk.presentation.userlist.UserListActivity

class ObjectRecognitionSDKInterfaceImpl(
    private val sharedPreference: UserSharedPreference
) : ObjectRecognitionSDKInterface {
    override fun initialize(context: Context) {
        val intent = Intent()
        intent.apply {
            action = if (sharedPreference.getToken().isEmpty()) {
                setClass(context, SignInActivity::class.java)
                SignInActivity::class.java.name
            } else {
                setClass(context, UserListActivity::class.java)
                UserListActivity::class.java.name
            }
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
        }

        context.startActivity(intent)
    }
}
