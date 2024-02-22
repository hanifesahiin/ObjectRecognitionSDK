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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.project.h.s.objectrecognitionsdk.R
import com.project.h.s.objectrecognitionsdk.theme.Dimension
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
            text = stringResource(id = R.string.users_waiting_for_approval),
            modifier = Modifier.constrainAs(text) {
                bottom.linkTo(surface.top, margin = Dimension.margin_16)
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
                .background(Color.White)
                .padding(start = Dimension.margin_10, end = Dimension.margin_10),
            shape = RoundedCornerShape(Dimension.round_20)
        ) {
            Spacer(modifier = Modifier.height(Dimension.margin_10))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .defaultMinSize(minHeight = Dimension.height_300)
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
            .padding(
                top = Dimension.margin_10,
                start = Dimension.margin_15,
                end = Dimension.margin_20
            )
    ) {
        val (text, button, line) = createRefs()

        Text(
            modifier = Modifier
                .constrainAs(text) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                },
            text = "",
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
                    bottom.linkTo(parent.bottom, margin = Dimension.margin_10)
                },
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