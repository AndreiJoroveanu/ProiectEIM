package com.aj.proiecteim.tema4

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aj.proiecteim.R
import com.example.reply.ui.theme.MaterialThemeEIM

class Tema4 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        this.actionBar?.hide()

        super.onCreate(savedInstanceState)

        setContent {
            MaterialThemeEIM {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                    FlagDisplay()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    val context = LocalContext.current

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            stringResource(id = R.string.tema4_linia1),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            stringResource(id = R.string.tema4_linia2),
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.secondary
        )

        Spacer(modifier = Modifier.height(8.dp))

        val link = stringResource(id = R.string.tema4_buton_link)
        Button(
            onClick = {
                openUrlInBrowser(context, link)
            }, modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Text(
                stringResource(id = R.string.tema4_buton_label),
                fontSize = 20.sp
            )
        }
    }
}

fun openUrlInBrowser(context: Context, url: String?) {
    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(browserIntent)
}

@Composable
fun FlagDisplay() {
    Column(
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier.padding(16.dp)
    ) {
        Image(
            modifier = Modifier.clip(MaterialTheme.shapes.medium),
            painter = painterResource(id = R.mipmap.country_flag),
            contentDescription = stringResource(id = R.string.tema4_linia2)
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}
