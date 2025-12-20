package com.example.project_practice

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.project_practice.ui.screens.Home
import com.example.project_practice.ui.screens.OnboardingScreen
import com.example.project_practice.ui.screens.RegisterAccount
import com.example.project_practice.ui.screens.SignIn
//import com.example.project_practice.ui.theme.ProjectPracticeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPref = getPreferences(MODE_PRIVATE)
        val hasSeenOnboarding = sharedPref.getBoolean("has_seen_onboarding", false)

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (!hasSeenOnboarding) {
                        OnboardingScreen {
                            with(sharedPref.edit()) {
                                putBoolean("has_seen_onboarding", true)
                                apply()
                            }
                        }
                    } else {
                        AppContent()
                    }
                }
            }
        }
    }
}

@Composable
fun AppContent() {
    var currentScreen by remember { mutableStateOf("register") }

    when (currentScreen) {
        "register" -> {
            RegisterAccount(
                onBackClick = {
                    currentScreen = "onboard"
                },
                onRegisterSuccess = {
                    currentScreen = "home"
                },
                onSignInClick = {
                    currentScreen = "signin"
                }
            )
        }
        "signin" -> {
            SignIn(
                onBackClick = {
                    currentScreen = "register"
                },
                onSignInSuccess = {
                    currentScreen = "home"
                },
                onSignInClick = {
                    currentScreen = "register"
                }
            )
        }
        "home" -> {
            Home()
        }
        "onboard" -> {
            OnboardingScreen(
                onFinish = {
                    currentScreen = "register"
                }
            )
        }
    }
}