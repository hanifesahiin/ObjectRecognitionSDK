package com.project.h.s.objectrecognitionsdk.presentation.signin

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.project.h.s.objectrecognitionsdk.R
import com.project.h.s.objectrecognitionsdk.presentation.userlist.UserListActivity
import com.project.h.s.objectrecognitionsdk.theme.Dimension
import com.project.h.s.objectrecognitionsdk.theme.ROUND_15
import com.project.h.s.objectrecognitionsdk.theme.grey
import com.project.h.s.objectrecognitionsdk.theme.typography
import com.project.h.s.objectrecognitionsdk.utils.TestTags

@Composable
fun SignInBox(
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onCheckedChange: (Boolean) -> Unit,
    onSignInAction: () -> Unit,
    state: SignInViewModel.UIFlow
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(grey)
            .testTag(TestTags.card_screen_container)
    ) {
        val (surface, text) = createRefs()

        Text(
            text = stringResource(id = R.string.user_sign_in),
            modifier = Modifier
                .constrainAs(text) {
                    bottom.linkTo(surface.top, margin = Dimension.margin_16)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .testTag(TestTags.user_sign_in_text),
            color = Color.White,
            style = typography.titleLarge
        )

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .constrainAs(surface) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(start = Dimension.margin_10, end = Dimension.margin_10),
            shape = RoundedCornerShape(Dimension.round_10)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimension.height_300)
                    .background(Color.White)
            ) {
                Spacer(modifier = Modifier.height(Dimension.margin_30))

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                        .padding(start = Dimension.margin_10, end = Dimension.margin_10)
                        .testTag(TestTags.user_sign_in_email)
                        .semantics { contentDescription = "Kullanıcı Adı" },
                    value = state.userName,
                    onValueChange = { onEmailChange(it) },
                    label = { Text(text = stringResource(id = R.string.user_name)) }
                )
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                        .padding(
                            top = Dimension.margin_10,
                            start = Dimension.margin_10,
                            end = Dimension.margin_10
                        )
                        .testTag(TestTags.user_sign_in_password),
                    value = state.password,
                    onValueChange = { onPasswordChange(it) },
                    label = { Text(text = stringResource(R.string.password)) }
                )

                Row(
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.End)
                        .clickable { onCheckedChange(!state.checked) },
                    horizontalArrangement = Arrangement.spacedBy(0.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .wrapContentSize()
                            .align(Alignment.CenterVertically),
                        text = "Beni hatırla",
                        style = typography.labelMedium
                    )
                    Checkbox(
                        modifier = Modifier
                            .scale(0.8f, 0.8f)
                            .alignByBaseline(),
                        checked = state.checked,
                        onCheckedChange = { isChecked -> onCheckedChange(isChecked) }
                    )
                }

                val enabled = state.userName.isNotEmpty() && state.password.isNotEmpty()

                Surface(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = Dimension.margin_15)
                        .alpha(if (enabled) 1.0f else 0.8f),
                    color = Color.Black,
                    shape = RoundedCornerShape(0, ROUND_15, 0, ROUND_15),
                ) {
                    Box(
                        modifier = Modifier
                            .width(Dimension.width_150)
                            .height(Dimension.height_40)
                            .clickable { if (enabled) onSignInAction() }
                            .testTag(TestTags.sign_in_button)
                    ) {
                        Text(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .alpha(if (enabled) 1.0f else 0.8f),
                            text = stringResource(id = R.string.sign_in),
                            color = Color.White,
                            style = typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}

fun Context.startUserListActivity() {
    val intent = Intent()
    intent.setClass(this, UserListActivity::class.java)
    intent.setAction(UserListActivity::class.java.getName())
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
    this.startActivity(intent)
}