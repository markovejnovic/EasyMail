package com.markovejnovic.mail.ui.views.composable.setup

import android.accounts.Account
import android.accounts.AccountManager
import android.util.Log
import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.markovejnovic.mail.ui.theme.EasyMailTheme

@Composable
fun SetupStepPage(title: String, description: String,
                  onNextClicked: () -> Unit,
                  content: @Composable (ColumnScope) -> Unit
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colors.primaryVariant)) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(Dp(16f)),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(Modifier.fillMaxWidth()) {
                Text(
                    text = title,
                    color = MaterialTheme.colors.onPrimary,
                    style = MaterialTheme.typography.h1
                )
                Text(
                    text = description,
                    color = MaterialTheme.colors.onPrimary,
                    style = MaterialTheme.typography.subtitle1
                )
            }

            Column(Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally) {
                content(this)
            }

            Row (Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                FloatingActionButton(onNextClicked,
                    backgroundColor = MaterialTheme.colors.primary) {
                    // TODO: Extract string
                    Icon(Icons.Filled.ArrowForward, "Next")
                }
            }
        }
    }
}


@Composable
fun SetupHelloPage(onNextClicked: () -> Unit) {
    // TODO: Extract these strings
    SetupStepPage(title = "Welcome to EasyMail!",
        description = "EasyMail is an incredibly simple, fast email client.",
        onNextClicked = onNextClicked) {
        Text(text = "First Page")
    }
}

@Composable
fun AccountCard(account: Account) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically) {
        Icon(Icons.Filled.Email, "Email Icon", Modifier.size(32.dp)) // TODO: String
        Column(
            Modifier
                .fillMaxWidth()
                .padding(start = 8.dp),
            verticalArrangement = Arrangement.Center) {
            Text(text = account.name, style = MaterialTheme.typography.body1)
            Text(text = account.type, style = MaterialTheme.typography.body2)
        }
    }
}

@Composable
fun AccountListing(makeNewAccount: () -> Unit) {
    val accounts = AccountManager.get(LocalContext.current).accounts.filter {
        true // TODO: Refactor and obviously don't get all accounts
    }

    Box(Modifier.clip(MaterialTheme.shapes.medium)) {
        Box(Modifier.background(MaterialTheme.colors.background)) {
            Column(Modifier.padding(8.dp)) {
                accounts.map {
                    AccountCard(it)
                }

                Button(onClick = makeNewAccount) {
                    Text(text = "New Account") // Change this
                }
            }
        }
    }
}

@Composable
fun SetupAccounts(onNextClicked: () -> Unit, makeNewAccount: () -> Unit) {
    // TODO: Extract strings
    SetupStepPage(title = "Account Setup",
        description = "We've found these accounts. Are there more?",
        onNextClicked = onNextClicked) {
        AccountListing(makeNewAccount)
    }
}

@Composable
fun SetupNewAccount(onDone: () -> Unit) {

}

@Preview(showBackground = true)
@Composable
fun SetupHelloPreview() {
    EasyMailTheme() {
        SetupHelloPage {}
    }
}

@Preview
@Composable
fun SetupAccountsPreview() {
    EasyMailTheme() {
        SetupAccounts({}, {})
    }
}