package com.project.h.s.objectrecognitionsdk.presentation.readidcard

import android.content.Intent
import android.os.Build.VERSION.SDK_INT
import android.os.Parcelable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.constraintlayout.compose.ConstraintLayout
import com.project.h.s.objectrecognitionsdk.R
import com.project.h.s.objectrecognitionsdk.theme.Dimension
import com.project.h.s.objectrecognitionsdk.theme.grey
import com.project.h.s.objectrecognitionsdk.theme.typography
import com.project.h.s.objectrecognitionsdk.utils.TestTags

@Composable
fun CardScreen(state: ReadIdCardViewModel.UiFlow, onFinished: () -> Unit) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(grey)
    ) {
        val (surface, text, button, alert) = createRefs()

        Text(
            text = stringResource(id = R.string.read_nfc),
            modifier = Modifier
                .constrainAs(text) {
                    bottom.linkTo(surface.top, margin = Dimension.margin_16)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .testTag(TestTags.read_nfc),
            color = Color.White,
            style = typography.titleLarge
        )

        Surface(
            modifier = Modifier
                .wrapContentSize()
                .constrainAs(surface) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(start = Dimension.margin_10, end = Dimension.margin_10),
            shape = RoundedCornerShape(Dimension.round_20)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimension.height_300)
                    .background(Color.White)
                    .padding(start = Dimension.margin_20)
                    .testTag(TestTags.white_card_container)
            ) {
                Spacer(modifier = Modifier.height(Dimension.margin_30))

                Text(
                    text = String.format(stringResource(id = R.string.ad), state.name),
                    style = typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(Dimension.margin_5))
                Text(
                    text = String.format(stringResource(id = R.string.soyad), state.lastName),
                    style = typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(Dimension.margin_5))
                Text(
                    text = String.format(stringResource(id = R.string.date), state.date),
                    style = typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(Dimension.margin_5))
                Text(
                    text = String.format(stringResource(id = R.string.expired_date), state.expiredDate),
                    style = typography.bodyMedium
                )
            }
        }

        ExtendedFloatingActionButton(
            modifier = Modifier
                .width(Dimension.width_150)
                .height(Dimension.height_40)
                .constrainAs(button) {
                    top.linkTo(surface.bottom, margin = Dimension.margin_20)
                    start.linkTo(surface.start)
                    end.linkTo(surface.end)
                }
                .testTag(TestTags.complete_button),
            text = {
                Text(
                    text = stringResource(id = R.string.complete),
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    style = typography.bodyMedium
                )
            },
            icon = { },
            shape = RoundedCornerShape(0, 20, 0, 20),
            containerColor = Color.Black,
            onClick = {
                onFinished()
            }
        )

        if (state.alert) {
            Text(
                modifier = Modifier
                    .wrapContentSize()
                    .constrainAs(alert) {
                        top.linkTo(button.bottom, margin = Dimension.margin_30)
                        start.linkTo(button.start)
                        end.linkTo(button.end)
                    },
                text = state.alertMessage,
                style = typography.labelSmall
            )
        }
    }
}

inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? = when {
    SDK_INT >= 33 -> getParcelableExtra(key, T::class.java)
    else ->
        @Suppress("DEPRECATION")
        getParcelableExtra(key) as? T
}