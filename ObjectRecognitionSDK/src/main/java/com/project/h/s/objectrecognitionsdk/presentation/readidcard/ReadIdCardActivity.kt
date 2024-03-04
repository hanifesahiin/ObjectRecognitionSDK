package com.project.h.s.objectrecognitionsdk.presentation.readidcard

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.project.h.s.objectrecognitionsdk.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@AndroidEntryPoint
class ReadIdCardActivity : ComponentActivity() {
    val readIdCardViewModel: ReadIdCardViewModel by viewModels()
    lateinit var mNfcAdapter: NfcAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReadIdCardScreen(readIdCardViewModel.uiFlow, readIdCardViewModel::hideAlertContainer)
        }

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this)

        if (mNfcAdapter.isEnabled) {
            readIdCardViewModel.showAlertContainer(getString(R.string.NFC_found))
        } else {
            readIdCardViewModel.showAlertContainer(getString(R.string.NFC_not_found))
        }

        this.registerReceiver(mReceiver, IntentFilter(NfcAdapter.ACTION_ADAPTER_STATE_CHANGED))
    }

    @Override
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action || NfcAdapter.ACTION_TAG_DISCOVERED == intent.action) {
            val tag: Tag? = intent.parcelable(NfcAdapter.EXTRA_TAG)

            if (tag != null) {
                readIdCardViewModel.connect(tag)
            } else {
                readIdCardViewModel.showAlertContainer(getString(R.string.tag_null))
            }
        }
    }

    @Override
    override fun onResume() {
        super.onResume()
        val pendingIntent = PendingIntent.getActivity(
            this, 0,
            Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), PendingIntent.FLAG_UPDATE_CURRENT
        )

        val intentFilter = IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED)
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT)
        val mFilters = arrayOf<IntentFilter>(
            intentFilter
        )

        mNfcAdapter.enableForegroundDispatch(this, pendingIntent, mFilters, null)
    }

    @Override
    override fun onPause() {
        super.onPause()
        mNfcAdapter.disableForegroundDispatch(this)
    }

    private val mReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            if (action == NfcAdapter.ACTION_ADAPTER_STATE_CHANGED) {
                val state = intent.getIntExtra(
                    NfcAdapter.EXTRA_ADAPTER_STATE,
                    NfcAdapter.STATE_OFF
                )
                when (state) {
                    NfcAdapter.STATE_OFF -> {
                        readIdCardViewModel.showAlertContainer(getString(R.string.NFC_not_found))
                    }

                    NfcAdapter.STATE_ON -> {
                        readIdCardViewModel.showAlertContainer(getString(R.string.NFC_found))
                    }
                }
            }
        }
    }
}

@Composable
fun ReadIdCardScreen(
    uiFlow: StateFlow<ReadIdCardViewModel.UiFlow>,
    hideAlertContainer: () -> Unit = {}
) {
    val state by uiFlow.collectAsState()
    val context = LocalContext.current as? ReadIdCardActivity
    CardScreen(state = state) {
        context?.finish()
    }

    LaunchedEffect(state.alert) {
        delay(2000)
        hideAlertContainer()
    }
}

@Composable
@Preview
fun Preview() {
    ReadIdCardScreen(
        uiFlow = MutableStateFlow(ReadIdCardViewModel.UiFlow())
    )
}