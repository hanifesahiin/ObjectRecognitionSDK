package com.project.h.s.objectrecognitionsdk.domain.usecases

import android.nfc.Tag
import android.nfc.tech.IsoDep
import com.project.h.s.objectrecognitionsdk.domain.entities.NfcReaderResponse
import com.project.h.s.objectrecognitionsdk.utils.Logger
import kotlinx.coroutines.suspendCancellableCoroutine
import org.jmrtd.lds.icao.DG1File
import javax.inject.Inject
import kotlin.coroutines.resume

class NfcReaderUseCase @Inject constructor() {
    private val _nfcReaderUseCaseClass = "NfcReaderUseCase"
    private lateinit var dg1File: DG1File

    suspend operator fun invoke(tag: Tag) =
        suspendCancellableCoroutine<NfcReaderResponse> { continuation ->
            val techList: Array<String> = tag.techList
            for (tech in techList) {
                if (tech == IsoDep::class.java.name) {
                    val isoDep = IsoDep.get(tag)

                    if (isoDep != null) {
                        /*try {
                            isoDep.timeout = 10000
                            val cardService = CardService.getInstance(isoDep)
                            cardService.open()

                            val service = PassportService(
                                cardService,
                                PassportService.NORMAL_MAX_TRANCEIVE_LENGTH,
                                PassportService.DEFAULT_MAX_BLOCKSIZE,
                                false,
                                false,
                            )
                            service.open()

                            var paceSucceeded = false
                            try {

                                val bacKey: BACKeySpec = BACKey("A35V49275", "930227", "320426")
                                val cardAccessFile =
                                     CardAccessFile(service.getInputStream(PassportService.EF_CARD_ACCESS))
                                 val securityInfoCollection = cardAccessFile.securityInfos
                                 for (securityInfo: SecurityInfo in securityInfoCollection) {
                                     if (securityInfo is PACEInfo) {
                                         service.doPACE(
                                             bacKey,
                                             securityInfo.objectIdentifier,
                                             PACEInfo.toParameterSpec(securityInfo.parameterId),
                                             null,
                                         )
                                         paceSucceeded = true
                                     }
                                 }
                                 service.sendSelectApplet(paceSucceeded)
                                 if (!paceSucceeded) {
                                     try {
                                         service.getInputStream(PassportService.EF_COM).read()
                                     } catch (e: Exception) {
                                         service.doBAC(bacKey)
                                     }
                                 }

                                 val dg1In = service.getInputStream(PassportService.EF_DG1)
                                 dg1File = DG1File(dg1In)
                                 val mrzInfo = dg1File.mrzInfo
                                 val response = NfcReaderResponse(
                                     name = mrzInfo.secondaryIdentifier.replace("<", " "),
                                     lastName = mrzInfo.primaryIdentifier.replace("<", " "),
                                     date = mrzInfo.dateOfBirth,
                                     expiredDate = mrzInfo.dateOfExpiry
                                 )
                                  continuation.resume(
                                   response
                                )

                            } catch (e: Exception) {
                                Logger.e(_nfcReaderUseCaseClass, "invoke CardAccess", e)
                            }
                        } catch (e: IOException) {
                            Logger.e(_nfcReaderUseCaseClass, "invoke IOException", e)
                        } finally {
                            try {
                                isoDep.close()
                            } catch (e: IOException) {
                                Logger.e(_nfcReaderUseCaseClass, "isoDep close", e)
                            }
                        }*/
                        continuation.resume(
                            NfcReaderResponse(
                                name = "Experimental",
                                lastName = "NfcReaderResponse",
                                date = "04.03.2024",
                                expiredDate = "29.03.2024"
                            )
                        )
                    } else {
                        Logger.e(
                            _nfcReaderUseCaseClass,
                            "invoke",
                            "Etiket Ndef formatında değil veya Ndef verisi yok"
                        )
                    }

                    break
                }
            }
        }
}