package com.project.h.s.objectrecognitionsdk.presentation.userlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.project.h.s.objectrecognitionsdk.theme.green
import com.project.h.s.objectrecognitionsdk.theme.grey
import com.project.h.s.objectrecognitionsdk.theme.typography

@Composable
fun UserListContainer(clickedItem: () -> Unit) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(grey)
    ) {
        val (surface, text) = createRefs()

        Text(
            text = "Onay Bekleyen\nKullanıcılar",
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
                }.background(Color.White)
                .padding(start = 10.dp, end = 10.dp),
            shape = RoundedCornerShape(20.dp)
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .defaultMinSize(minHeight = 300.dp)
                    .background(Color.White)
            ) {
                items(listOf<String>("")) {
                    ListItem {
                        clickedItem()
                    }
                }
            }
        }
    }
}

@Composable
fun ListItem(onClick: () -> Unit) {
    ConstraintLayout(
        modifier = Modifier
            .wrapContentSize()
            .fillMaxWidth()
            .padding(top = 10.dp, start = 15.dp, end = 20.dp)
    ) {
        val (text, button, line) = createRefs()

        Text(
            modifier = Modifier
                .constrainAs(text) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                },
            text = "Mail",
            color = Color.Black,
            style = typography.bodyMedium
        )
        Button(
            onClick = { onClick() },
            modifier = Modifier
                .wrapContentSize()
                .constrainAs(button) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom, margin = 10.dp)
                },
            shape = RoundedCornerShape(10),
            colors = ButtonDefaults.buttonColors(containerColor = green)
        ) {
            Text("Onayla")
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