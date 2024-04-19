package com.example.jpmchase.nycschool.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jpmchase.nycschool.ui.compose.LoginScreen
import com.example.jpmchase.nycschool.ui.compose.NYCSchoolScreen
import com.example.jpmchase.nycschool.ui.theme.NYCSchoolTheme
import com.example.jpmchase.nycschool.utils.Constants

@Composable
fun AppNavigation (
    modifier: Modifier = Modifier,
    closeApp:() -> Unit
) {
    val navController = rememberNavController()

    NYCSchoolTheme {
        NavHost(navController = navController,
                startDestination = Constants.LOGIN_SCREEN) {
            composable(route = Constants.LOGIN_SCREEN) {
                LoginScreen(navController)
            }
            composable(route = Constants.NYC_SCHOOLS_LIST) {
                NYCSchoolScreen(
                    navController = navController,
                    closeApp = closeApp
                )
            }
        }
    }
}