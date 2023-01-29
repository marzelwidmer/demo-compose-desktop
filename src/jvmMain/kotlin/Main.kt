import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import com.fasterxml.jackson.databind.ObjectMapper
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import kotlinx.coroutines.*
import java.time.ZonedDateTime

@Composable
@Preview
fun App() {

    var text by remember { mutableStateOf("Hello, World!") }
    var objectMapper: ObjectMapper? = ObjectMapper() //    var coroutineScope = CoroutineScope(Dispatchers.Main)
//    coroutineScope.launch {
//        val client = HttpClient(CIO)
//        val response = client.get("https://ktor.io/")

//    }
    runBlocking { // this: CoroutineScope
        launch { // launch a new coroutine and continue
            delay(1000L) // non-blocking delay for 1 second (default time unit is ms)
            println("World!") // print after delay

            val client = HttpClient(CIO)
            val response = client.get("https://ktor.io/")
            println(response)

            val servicebusClient = ServicebusClient(objectMapper)
            servicebusClient.sendToOutTopic(
                PendenzClosedEvent(
                    metadata = EventMetadata(
                        ebid = null,
                        loggedInUser = LoggedInUser(
                            partnerId = null,
                            partnerNr = null,
                            portalAccountId = "portalAccountId"
                        ),
//                        createdAt = ZonedDateTime.now(),
                        businessCaseId = "pendenzId"
                    ),
                    payload = PendenzClosedEventPayload(
                        portalKontoId = "portalAccountId",
                        pendenzId = "pendenzId"
                    )
                )
            )
        }
        println("Hello") // main coroutine continues while a previous one is delayed
    }
    println("Hello") // mai

    MaterialTheme {
        Button(onClick = {
            text = "Hello, Desktop!"


        }) {
            Text(text)
        }
    }
}

@Composable
fun HelloWorld() {

    Box(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
        Text(
            text = "Hello World",
            fontSize = 30.sp,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication,
        state = WindowState(width = 400.dp, height = 300.dp),
        title = "Medium"
    ) {

        App()
    }
}
