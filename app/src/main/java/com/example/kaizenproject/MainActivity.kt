package com.example.kaizenproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.kaizenproject.common.ui.theme.KaizenProjectTheme
import com.example.kaizenproject.presentation.SportingEventsScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KaizenProjectTheme {
                SportingEventsScreen()
            }
        }
    }
}
