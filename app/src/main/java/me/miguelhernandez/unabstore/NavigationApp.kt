package me.miguelhernandez.unabstore

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavigationApp(){
    val navController = rememberNavController()
    val startDestination = "Login"
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(route = "Login") {
            LoginScreen(onClickRegister = {
                navController.navigate("register")
            })
        }
        composable (route = "register") {
            RegisterScreen()
        }
        composable (route = "home") {
            HomeScreen()
        }
    }
}