package com.markovejnovic.mail.ui.views.composable.emaillist

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import com.markovejnovic.mail.models.Email
import com.markovejnovic.mail.models.EmailAuthor
import com.markovejnovic.mail.ui.theme.EasyMailTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EmailListItemDismissBackground(dismissState: DismissState) {
    val color by animateColorAsState(
        targetValue = when (dismissState.targetValue) {
            DismissValue.Default -> Color.Transparent
            DismissValue.DismissedToEnd -> Color.Red
            DismissValue.DismissedToStart -> Color.Green
        }
    )

    Box(modifier = Modifier
        .fillMaxSize()
        .background(color))
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EmailListItem(email: Email,
                  onDismissedToStart: () -> Unit = {},
                  onDismissedToEnd: () -> Unit = {}) {
    val dismissState = rememberDismissState(
        confirmStateChange = {
            if (it == DismissValue.DismissedToStart) {
                onDismissedToStart()
            }
            if (it == DismissValue.DismissedToEnd) {
                onDismissedToEnd()
            }

            true
        }
    )

    SwipeToDismiss(
        state = dismissState,
        background = { EmailListItemDismissBackground(dismissState = dismissState) },
        dismissContent = {
            Row(Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically) {
                Column() {
                    Image(
                        bitmap = email.author.image,
                        contentDescription = email.author.name
                    )
                }

                Column() {
                    Text(text = email.author.name, style = MaterialTheme.typography.h4)
                    Text(text = email.subject, style = MaterialTheme.typography.subtitle1)
                    Text(text = email.short, style = MaterialTheme.typography.subtitle2)
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EmailList(emails: MutableList<Email>) {
    LazyColumn {
        // TODO: This implementation could possibly be slow because emails.remove may be O(n). Not
        // sure, depends on how items works.
        items(emails, null) { email ->
            EmailListItem(email = email, {
                emails.remove(email)
            })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val elements = remember {
        mutableStateListOf(
            Email("Hello Email",
                author = EmailAuthor("Jim", image = ImageBitmap(256, 256),
                    address = "jim@jo.com"), short = "Hey friend, how are you doing?"),
            Email("Bye Email",
                author = EmailAuthor("Angry Jim", image = ImageBitmap(256, 256),
                    address = "jim@jo.com"), short = "Goodbye my friend, I don't care.")
        )
    }

    EasyMailTheme {
        EmailList(elements)
    }
}