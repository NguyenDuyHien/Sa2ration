package com.xda.sa2ration.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.xda.sa2ration.ui.theme.Sa2rationTheme
import com.xda.sa2ration.utils.checkSudo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!checkSudo()) {
            finish()
        }

        setContent {
            Sa2rationTheme {
                Sa2rationApp()
            }
        }
    }
}

@Preview
@Composable
fun Sa2rationAppPreview() {
    Sa2rationTheme {
        Sa2rationApp()
    }
}

@Preview
@Composable
fun DarkSa2rationAppPreview() {
    Sa2rationTheme(darkTheme = true) {
        Sa2rationApp()
    }
}