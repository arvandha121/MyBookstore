package com.fitdev.mybookstore.ui.navigate

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object About : Screen("about")
    object Detail : Screen("home/{id}") {
        fun createRoute(id: Int) = "home/$id"
    }
}