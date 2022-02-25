package com.markovejnovic.mail

import android.accounts.AccountManager
import android.graphics.drawable.ShapeDrawable
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.markovejnovic.mail.models.Email
import com.markovejnovic.mail.models.EmailAuthor
import com.markovejnovic.mail.ui.theme.EasyMailTheme
import com.markovejnovic.mail.ui.views.composable.setup.SetupAccounts
import com.markovejnovic.mail.ui.views.composable.setup.SetupAccountsPreview
import com.markovejnovic.mail.ui.views.composable.setup.SetupHelloPage
import com.markovejnovic.mail.ui.views.composable.setup.SetupNewAccount

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EasyMailTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    Main()
                }
            }
        }
    }
}

@Composable
fun Main() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "setup_hello") {
        composable("setup_hello") {
            SetupHelloPage { navController.navigate("setup_accounts") }
        }
        composable("setup_accounts") {
            SetupAccounts({
                navController.navigate("setup_hello")
            }, {
                navController.navigate("setup_new_account")
            })
        }
        composable("setup_new_account") {
            SetupNewAccount { navController.popBackStack() }
        }
    }
}