package com.project.h.s.objectrecognitionsdk.presentation.userlist

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.project.h.s.objectrecognitionsdk.R
import com.project.h.s.objectrecognitionsdk.domain.entities.UserItem
import com.project.h.s.objectrecognitionsdk.presentation.captureimage.CaptureImageActivity
import com.project.h.s.objectrecognitionsdk.presentation.readidcard.ReadIdCardActivity
import com.project.h.s.objectrecognitionsdk.theme.Dimension
import com.project.h.s.objectrecognitionsdk.theme.green
import com.project.h.s.objectrecognitionsdk.theme.grey
import com.project.h.s.objectrecognitionsdk.theme.typography
import com.project.h.s.objectrecognitionsdk.utils.TestTags

@Composable
fun UserListContainer(
    list: List<UserItem>,
    progressIndicator: Boolean,
    clickedItem: (Int) -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(grey)
    ) {
        val (surface, text, progress) = createRefs()

        Text(
            text = stringResource(id = R.string.users_waiting_for_approval),
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(text) {
                    bottom.linkTo(surface.top, margin = Dimension.margin_16)
                }
                .padding(start = Dimension.margin_10, end = Dimension.margin_10)
                .testTag(TestTags.users_waiting_for_approval),
            textAlign = TextAlign.Center,
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
            Spacer(modifier = Modifier.height(Dimension.margin_10))
            val listState = rememberLazyListState()

            LazyColumn(
                modifier = Modifier
                    .height(Dimension.height_300)
                    .fillMaxWidth()
                    .background(Color.White)
                    .testTag(TestTags.user_list_lazy_column),
                state = listState
            ) {
                items(list) { item ->
                    ListItem(item) {
                        clickedItem(it)
                    }
                }
            }
        }

        if (progressIndicator) {
            CircularProgressIndicator(modifier = Modifier.constrainAs(progress) {
                centerTo(surface)
            })
        }
    }
}

@Composable
fun ListItem(item: UserItem, onClick: (Int) -> Unit) {
    ConstraintLayout(
        modifier = Modifier
            .wrapContentSize()
            .fillMaxWidth()
            .padding(
                top = Dimension.margin_10,
                start = Dimension.margin_15,
                end = Dimension.margin_20
            )
    ) {
        val (textId, button, line) = createRefs()

        val text = "${item.id} ${item.name} ${item.email}"
        Box(
            modifier = Modifier
                .fillMaxSize()
                .constrainAs(textId) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .testTag(TestTags.user_list_item)
        ) {
            Text(
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.TopStart),
                textAlign = TextAlign.Start,
                maxLines = 2,
                text = text,
                color = Color.Black,
                style = typography.bodySmall
            )
        }

        Button(
            onClick = { onClick(item.id) },
            modifier = Modifier
                .wrapContentSize()
                .constrainAs(button) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom, margin = Dimension.margin_10)
                }
                .testTag(TestTags.confirm_button),
            shape = RoundedCornerShape(10),
            colors = ButtonDefaults.buttonColors(containerColor = green)
        ) {
            Text(stringResource(id = R.string.confirm))
        }

        Divider(
            modifier = Modifier.constrainAs(line) {
                bottom.linkTo(parent.bottom)
            },
            color = Color.Black,
            thickness = 1.dp
        )
    }
}

fun Context.startCaptureImageActivity() {
    val intent = Intent()
    intent.setClass(this, CaptureImageActivity::class.java)
    intent.setAction(CaptureImageActivity::class.java.getName())
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
    this.startActivity(intent)
}

fun Context.startReadIdCardActivity() {
    val intent = Intent()
    intent.setClass(this, ReadIdCardActivity::class.java)
    intent.setAction(ReadIdCardActivity::class.java.getName())
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    this.startActivity(intent)
}