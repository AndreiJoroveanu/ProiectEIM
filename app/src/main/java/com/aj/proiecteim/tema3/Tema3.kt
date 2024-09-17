package com.aj.proiecteim.tema3

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reply.ui.theme.MaterialThemeEIM
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url

class Tema3 : ComponentActivity() {
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
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    var url by remember { mutableStateOf("https://www.stiripesurse.ro/rss/stirile-zilei.xml") }
    var rssFeed by remember { mutableStateOf<RssFeed?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = url,
            onValueChange = { url = it },
            label = { Text("Adresă RSS") },
            shape = RoundedCornerShape(100.dp),
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(MaterialTheme.colorScheme.secondary)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val coroutineScope = CoroutineScope(Dispatchers.Main.immediate)

                coroutineScope.launch {
                    if (url.isEmpty()) {
                        errorMessage = "Introduceți o adresă validă URL"
                    } else {
                        try {
                            val fetchedRssFeed = fetchRssFeed(url)
                            fetchedRssFeed?.let {
                                rssFeed = it
                            } ?: run {
                                errorMessage = "Eroare în preluarea feed-ului RSS"
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                            errorMessage = "Eroare în preluarea feed-ului RSS"
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Text(
                "Preluare feed",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

        Spacer(modifier = Modifier.height(4.dp))
        Text(
            "Feed RSS:",
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.secondary
        )
        Spacer(modifier = Modifier.height(4.dp))

        rssFeed?.let { ShowRssFeed(it) }
        errorMessage?.let { ShowError(it) }
    }
}

@Composable
fun ShowRssFeed(rssFeed: RssFeed) {
    val context = LocalContext.current

    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        rssFeed.channel?.items?.forEach { item ->
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Titlu:\n" + item.title,
                    color = MaterialTheme.colorScheme.secondary)
                Text("Dată: " + item.pubDate?.substringBefore(delimiter = "+"),
                    color = MaterialTheme.colorScheme.secondary)
                Button(
                    onClick = {
                        openUrlInBrowser(context, item.link)
                    }, modifier = Modifier.align(Alignment.CenterHorizontally),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary)
                ) {
                    Text("Deschideți în browser")
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

fun openUrlInBrowser(context: Context, url: String?) {
    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(browserIntent)
}

@Composable
fun ShowError(error: String) {
    Text(error, color = MaterialTheme.colorScheme.error)
}

// Retrofit API service
interface ApiService {
    @GET
    suspend fun getRssFeed(@Url url: String): Response<RssFeed>
}

// Function to fetch RSS feed from URL
suspend fun fetchRssFeed(url: String): RssFeed? {
    val apiService = RetrofitBuilder.apiService
    val response = apiService.getRssFeed(url)
    return if (response.isSuccessful) response.body() else null
}

// Retrofit builder
object RetrofitBuilder {
    const val BASE_URL = "https://example.com/"
    val apiService: ApiService = RetrofitClient.retrofit.newBuilder().addConverterFactory(
        SimpleXmlConverterFactory.create()).build().create(ApiService::class.java)
}

// Retrofit client
object RetrofitClient {
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(RetrofitBuilder.BASE_URL)
        .build()
}
