package com.example.apifetcher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.apifetcher.ui.theme.APIFetcherTheme
import com.example.apifetcher.userdirectory.UserScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            APIFetcherTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    UserApp(modifier = Modifier.padding(innerPadding)) // <-- Call new app
                }
            }
        }
    }
}

// ******************************************************
// Offline-First User Directory App
@Composable
fun UserApp(modifier: Modifier) {
    Column(modifier) {
        UserScreen() // <-- Entry point for the new User Directory
    }
}