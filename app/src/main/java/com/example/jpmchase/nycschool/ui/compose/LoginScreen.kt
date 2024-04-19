package com.example.jpmchase.nycschool.ui.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.jpmchase.nycschool.R
import com.example.jpmchase.nycschool.utils.Constants

@Composable
fun LoginScreen (
    navController: NavController
) {
    val userName : MutableState<String> = remember {
        mutableStateOf("")
    }
    val password : MutableState<String> = remember {
        mutableStateOf("")
    }
    val loginEnabled : MutableState<Boolean> = remember {
        mutableStateOf(false)
    }

    Box {
        Column (modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
            .align(Alignment.Center)
        ) {
            LoginUserNameAndPassword(userName, password, loginEnabled)
            LoginButton(navController, loginEnabled.value)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginUserNameAndPassword(
    userName: MutableState<String>,
    password: MutableState<String>,
    loginEnabled : MutableState<Boolean>
) {
    TextField(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 20.dp, start = 20.dp, end = 20.dp),
        readOnly = false,
        value = userName.value,
        singleLine = true,
        label = { Text ("Username") },
        onValueChange = {
            userName.value = it
        })

    TextField(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 20.dp, start = 20.dp, end = 20.dp),
        readOnly = false,
        value = password.value,
        singleLine = true,
        label = { Text ("Password") },
        onValueChange = {
            password.value = it
        })
    loginEnabled.value = userName.value.isNotEmpty() && password.value.isNotEmpty()
}

@Composable
fun LoginButton(navController: NavController,
                isEnabled : Boolean) {
    Button(modifier = Modifier
        .fillMaxWidth()
        .height(60.dp)
        .padding(top = 20.dp, start = 20.dp, end = 20.dp),
        enabled = isEnabled,
        onClick = {
            navController.navigate(Constants.NYC_SCHOOLS_LIST)
        }) {
        Text(color = Color.Black,
            text = stringResource(id = R.string.login)
        )
    }
}