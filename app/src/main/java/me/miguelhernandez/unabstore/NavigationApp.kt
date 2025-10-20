package me.miguelhernandez.unabstore

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun NavigationApp(){

    val navController = rememberNavController()
    val startDestination: String

    val auth = Firebase.auth
    val currentUser = auth.currentUser

    if (currentUser != null){
        startDestination = "home"
    }else{
        startDestination = "login"
    }
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(route = "Login") {
            LoginScreen(onClickRegister = {
                navController.navigate("register")
                },
                onSuccessfulLogin = {
                    navController.navigate("home"){
                        popUpTo("Login"){inclusive = true}
                    }
                }
            )
        }
        composable (route = "register") {
            RegisterScreen(onClickReturn = {
                navController.popBackStack()
            }, onSuccessfulRegister = {
                navController.navigate("home"){
                    popUpTo("register"){inclusive = true}
                }
            })
        }
        composable (route = "home") {
            HomeScreen(onClickLogOut = {
                navController.navigate("Login"){
                    popUpTo(0)
                }
            })
        }
    }
}