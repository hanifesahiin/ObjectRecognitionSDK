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
import androidx.compose.foundation.layout.defaultMinSize
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.project.h.s.objectrecognitionsdk.theme.grey
import com.project.h.s.objectrecognitionsdk.presentation.userlist.UserListActivity
import com.project.h.s.objectrecognitionsdk.theme.typography

@Composable
fun SignInBox(checkbox: Boolean, onCheckedChange: (Boolean) -> Unit, onSignInAction: () -> Unit) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(grey)
    ) {
        val (surface, text) = createRefs()

        Text(
            text = "KULLANICI GİRİŞİ",
            modifier = Modifier.constrainAs(text) {
                bottom.linkTo(surface.top, margin = 16.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
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
                .padding(start = 10.dp, end = 10.dp),
            shape = RoundedCornerShape(10.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .defaultMinSize(minHeight = 300.dp)
                    .background(Color.White)
            ) {
                Spacer(modifier = Modifier.height(30.dp))

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                        .padding(start = 10.dp, end = 10.dp),
                    value = "",
                    onValueChange = {},
                    label = { Text(text = "Kullanıcı Adı") }
                )
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 10.dp, start = 10.dp, end = 10.dp),
                    value = "",
                    onValueChange = {},
                    label = { Text(text = "Şifre") }
                )

                Row(
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.End)
                        .clickable { onCheckedChange(!checkbox) },
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
                        checked = checkbox,
                        onCheckedChange = { isChecked -> onCheckedChange(isChecked) }
                    )
                }

                Surface(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 15.dp),
                    color = Color.Black,
                    shape = RoundedCornerShape(0, 15, 0, 15),
                ) {
                    Box(
                        modifier = Modifier
                            .width(150.dp)
                            .height(40.dp)
                            .clickable { onSignInAction() }
                    ) {
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = "Giriş Yap",
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