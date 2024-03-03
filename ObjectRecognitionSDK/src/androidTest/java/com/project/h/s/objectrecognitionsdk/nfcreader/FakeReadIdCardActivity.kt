package com.project.h.s.objectrecognitionsdk.nfcreader

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class FakeReadIdCardActivity : ComponentActivity() {
    companion object {
        var isFinishActivityCalled: Boolean = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {}
    }

    override fun finish() {
        super.finish()
        isFinishActivityCalled = true
    }
}