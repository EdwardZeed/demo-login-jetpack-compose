package com.example.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.ui.theme.ComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    var paddingState by remember { mutableStateOf(16.dp) }
    val padding by animateDpAsState(
        targetValue = paddingState,
        tween(durationMillis = 1000)
    )

    Box(modifier = Modifier
        .fillMaxSize()
        .focusable()
        .background(color = Color.Transparent)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
            Image(
                painter = ColorPainter(Color.Gray), contentDescription = "Gray", modifier = Modifier
                    .fillMaxHeight(0.2f)
                    .fillMaxWidth()
            )

            Text(
                text = "Login",
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 32.dp, bottom = 16.dp),
                style = TextStyle(
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                )
            )

            val localFocusManager = LocalFocusManager.current
            Modifier.padding(padding)
                    .fillMaxWidth(0.8f)
                    .onFocusChanged { focused ->
                        if (focused.isFocused) {
                            paddingState = 8.dp
                        } else {
                            paddingState = 16.dp
                        }
                    }
                    .apply {
                        usernameInput(this, localFocusManager)
                        passwordInput(this, localFocusManager)
                    }


            Row(){
                Text(
                    text = "Don't have an account? ",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = padding, bottom = padding, start = padding, end = 0.dp)
                )
                Text(
                    text = "sign up",
                    modifier = Modifier.padding(top = padding, bottom = padding, end = padding, start = 0.dp)
                )
            }


            Button(
                onClick = {}, modifier = Modifier
                    .fillMaxWidth(0.3f)
                    .height(50.dp)
            ) {
                Text(
                    text = "Login",
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                    )
                )
            }
        }
    }
//    Text(text = "Hello $name!")
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun usernameInput(modifier: Modifier = Modifier, localFocusManager: FocusManager): String {
    var username by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current


    TextField(value = username,
            onValueChange = { username = it },
            label = { Text(text = "Username", textAlign = TextAlign.Center) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone, imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { localFocusManager.moveFocus(FocusDirection.Down) }),
            modifier = modifier,
            leadingIcon = {
                Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "phone leading icon")
            }

    )

    return username

}

@Composable
fun passwordInput(modifier: Modifier, loaclFoucsManager: FocusManager): String{
    var password by remember { mutableStateOf("") }
    var passwordHidden by remember { mutableStateOf(true) }

    TextField(value = password,
        onValueChange = { password = it },
        label = { Text(text = "Password")},
        visualTransformation = if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = {loaclFoucsManager.clearFocus()}),
        modifier = modifier,
        leadingIcon = { Icon(imageVector = Icons.Filled.Lock, contentDescription = "password leading icon")},
        trailingIcon = {
            IconButton(onClick = { passwordHidden = !passwordHidden }) {
                val visibilityIcon =
                    if (passwordHidden) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                // Please provide localized description for accessibility services
                val description = if (passwordHidden) "Show password" else "Hide password"
                Icon(imageVector = visibilityIcon, contentDescription = description)
            }
        }

    )

    return password
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeTheme {
        Greeting("Android")
    }
}