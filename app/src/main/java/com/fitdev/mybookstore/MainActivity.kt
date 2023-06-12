package com.fitdev.mybookstore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fitdev.mybookstore.ui.navigate.Screen
import com.fitdev.mybookstore.ui.theme.MyBookstoreTheme
import com.fitdev.mybookstore.ui.view.about.MyBookAbout
import com.fitdev.mybookstore.ui.view.detail.MyBookDetail
import com.fitdev.mybookstore.ui.view.home.MyBookApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyBookstoreTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.surface
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = Screen.Home.route) {
                        composable(Screen.Home.route) {
                            MyBookApp(modifier = Modifier, navController = navController)
                        }
                        composable(Screen.About.route) {
                            MyBookAbout(modifier = Modifier, onBackClick = {
                                navController.navigate(Screen.Home.route) {
                                    popUpTo(Screen.Home.route) {
                                        inclusive = true
                                    }
                                }
                            })
                        }
                        composable(Screen.Detail.route) { backStackEntry ->
                            val id = backStackEntry.arguments?.getString("id")?.toInt()
                            if (id != null) {
                                MyBookDetail(id,
                                    onBackClick = {
                                        navController.navigate(Screen.Home.route) {
                                            popUpTo(Screen.Home.route) {
                                                inclusive = true
                                            }
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}