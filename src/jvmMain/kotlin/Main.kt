import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import com.fasterxml.jackson.databind.ObjectMapper


// {
//  "@type": "PendenzClosedEvent",
//  "metadata": {
//    "loggedInUser": {
//      "portalAccountId": "A1004606"
//    },
//    "createdAt": "2023-01-19T14:27:11.535841+01:00",
//    "businessCaseId": "34dfc960-4847-4381-9720-b6367d3cfd39"
//  },
//  "payload": {
//    "portalKontoId": "A1004606",
//    "pendenzId": "34dfc960-4847-4381-9720-b6367d3cfd39"
//  }
//}

@Composable
@Preview
fun App() {
    //   fooScreen()
    //    WithConstraintsComposable()
    //    SimpleOutlinedTextFieldSample()
    SignUp()
}


@Composable
fun SignUp() {
    Scaffold(
        topBar = {},
        content = {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val username = remember {
                    mutableStateOf(TextFieldValue())
                }

                val password = remember {
                    mutableStateOf(TextFieldValue())
                }

                val passwordConfirm = remember {
                    mutableStateOf(TextFieldValue())
                }

                Text(
                    text = "PendenzClosedEvent",
                    style = TextStyle(fontSize = 40.sp, fontFamily = FontFamily.Cursive)
                )

                Spacer(modifier = Modifier.height(15.dp))

                TextField(
                    label = { Text(text = "PortalAccountId") },
                    value = username.value,
                    onValueChange = { username.value = it }
                )

                Spacer(modifier = Modifier.height(15.dp))

                TextField(
                    label = { Text(text = "PendenzId") },
                    value = username.value,
                    onValueChange = { username.value = it }
                )

                Spacer(modifier = Modifier.height(15.dp))

                TextField(
                    label = { Text(text = "BusinessCaseId") },
                    value = username.value,
                    onValueChange = { username.value = it }
                )

                Spacer(modifier = Modifier.height(15.dp))

                Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                    Button(
                        onClick = {},
                        shape = RoundedCornerShape(50.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text(text = "Submit")
                    }
                }

                Spacer(modifier = Modifier.height(15.dp))

                Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                    Button(
                        onClick = {},
                        shape = RoundedCornerShape(50.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text(text = "Clear")
                    }
                }
            }
        }
    )
}


@Composable
fun SimpleOutlinedTextFieldSample() {

    BoxWithConstraints {
        var text by remember { mutableStateOf("") }
        OutlinedTextField(value = text, onValueChange = { text = it }, label = { Text("PortalAccountId") })
    }
}

@Composable
fun WithConstraintsComposable() {
    BoxWithConstraints {
        Text("My minHeight is $minHeight while my maxWidth is $maxWidth...........")
    }
}

@Composable
private fun fooScreen() {
    var text by remember { mutableStateOf("Hello, World!") }
    MaterialTheme {
        Column {
            Text("Alfred Sisley")
            Text("3 minutes ago...")
        }
        //        Box(modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        //            contentAlignment = Alignment.Vertical()
        //        ) {
        //            Row{
        //                Text(text = "Hello World1", fontSize = 30.sp)
        //                Text(text = "Hello World2", fontSize = 30.sp)
        //            }
        //        }
    }
}

fun foo() {
    var objectMapper: ObjectMapper? = ObjectMapper()
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
//        state = WindowState(width = 400.dp, height = 300.dp),
        title = "Send PendenzClosedEvent"
    ) {

        App()
    }
}
