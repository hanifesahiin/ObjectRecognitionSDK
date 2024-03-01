package com.project.h.s.objectrecognitionsdk.domain.usecases

import android.nfc.Tag
import android.nfc.tech.NfcA
import android.util.Log
import com.project.h.s.objectrecognitionsdk.domain.entities.NfcReaderResponse
import com.project.h.s.objectrecognitionsdk.utils.Logger
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.resume

class NfcReaderUseCase @Inject constructor() {
    private val _nfcReaderUseCaseClass = "NfcReaderUseCase"

    suspend operator fun invoke(tag: Tag) =
        suspendCancellableCoroutine<NfcReaderResponse> { continuation ->
            val techList: Array<String> = tag.techList

            for (tech in techList) {
                if (tech == NfcA::class.java.name) {
                    val nfcA = NfcA.get(tag)

                    if (nfcA != null) {
                        try {
                            nfcA.connect()
                            //TODO: read the card
                            continuation.resume(NfcReaderResponse())
                        } catch (e: IOException) {
                            Logger.e(_nfcReaderUseCaseClass, "invoke IOException", e)
                        } finally {
                            try {
                                nfcA.close()
                            } catch (e: IOException) {
                                Logger.e(_nfcReaderUseCaseClass, "NfcA close", e)
                            }
                        }
                    } else {
                        Log.d("IntentAction: ", "Etiket Ndef formatında değil veya Ndef verisi yok")
                    }

                    break
                }
            }
        }
}