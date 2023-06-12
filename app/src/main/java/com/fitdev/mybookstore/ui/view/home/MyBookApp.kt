package com.fitdev.mybookstore.ui.view.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.fitdev.mybookstore.repository.BookRepository
import com.fitdev.mybookstore.data.MenuItem
import com.fitdev.mybookstore.ui.theme.MyBookstoreTheme
import com.fitdev.mybookstore.ui.component.MyTopBar
import com.fitdev.mybookstore.ui.component.SearchBar
import com.fitdev.mybookstore.ui.component.ListBookItem
import com.fitdev.mybookstore.R
import com.fitdev.mybookstore.ui.navigate.Screen
import com.fitdev.mybookstore.model.MyBookViewModel
import com.fitdev.mybookstore.model.ViewModelFactory
import com.fitdev.mybookstore.ui.component.ScrollToTopButton
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MyBookApp(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: MyBookViewModel = viewModel(factory = ViewModelFactory(BookRepository()))
) {
    val books by viewModel.books.collectAsState()
    val query by viewModel.query

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val drawerScope = rememberCoroutineScope()

    val items = listOf(
        MenuItem(
            title = stringResource(R.string.home),
            icon = Icons.Default.Home,
        ),
        MenuItem(
            title = stringResource(R.string.profile),
            icon = Icons.Default.AccountCircle
        )
    )

    val selectedItem by remember { mutableStateOf(items[0]) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(24.dp)
                )
                Spacer(Modifier.height(12.dp))
                items.forEach { item ->
                    NavigationDrawerItem(
                        icon = { Icon(item.icon, contentDescription = "") },
                        label = { Text(item.title) },
                        selected = item == selectedItem,
                        onClick = {
                            when (item) {
                                items[0] -> {
                                    navController.navigate(Screen.Home.route) {
                                        popUpTo(navController.graph.startDestinationId)
                                        launchSingleTop = true
                                    }
                                }

                                items[1] -> {
                                    navController.navigate(Screen.About.route) {
                                        popUpTo(navController.graph.startDestinationId)
                                        launchSingleTop = true
                                    }
                                }
                            }
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        },
        content = {
            Box(modifier = modifier) {
                val listState = rememberLazyListState()
                val showScrollButton: Boolean by remember {
                    derivedStateOf { listState.firstVisibleItemIndex > 0 }
                }
                LazyColumn(
                    state = listState,
                    modifier = modifier
                ) {
                    item {
                        MyTopBar(
                            onMenuClick = {
                                drawerScope.launch { drawerState.open() }
                            },
                        )
                    }
                    stickyHeader {
                        Column(
                            modifier = modifier
                                .padding(bottom = 8.dp)
                                .background(color = MaterialTheme.colorScheme.primary)
                        ) {
                            SearchBar(
                                query = query,
                                onQueryChange = viewModel::search,
                                modifier = modifier
                            )
                        }
                    }
                    items(books, key = { it.id }) {
                        ListBookItem(
                            id = it.id,
                            name = it.name,
                            photoUrl = it.photoUrl,
                            modifier = Modifier
                                .fillMaxWidth()
                                .animateItemPlacement(tween(durationMillis = 100)),
                            navController = navController
                        )
                    }
                }
                AnimatedVisibility(
                    visible = showScrollButton,
                    enter = fadeIn() + slideInVertically(),
                    exit = fadeOut() + slideOutVertically(),
                    modifier = Modifier
                        .padding(bottom = 30.dp)
                        .align(Alignment.BottomCenter)
                ) {
                    ScrollToTopButton(
                        onClick = {
                            drawerScope.launch {
                                listState.animateScrollToItem(index = 0)
                            }
                        }
                    )
                }
            }
        },
    )
}

@Composable
@Preview(showBackground = true, device = Devices.PIXEL_4)
fun MyBookAppPreview() {
    MyBookstoreTheme {
        val navController = rememberNavController()
        MyBookApp(navController = navController)
    }
}

@Composable
@Preview(showBackground = true)
fun MyTopBarPreview() {
    MyBookstoreTheme() {
        MyTopBar {}
    }
}

@Composable
@Preview(showBackground = true)
fun SearchBarPreview() {
    MyBookstoreTheme {
        SearchBar("", {})
    }
}

@Composable
@Preview(showBackground = true)
fun listBookItemPreview() {
    MyBookstoreTheme {
        ListBookItem(
            id = 1,
            name = "Matematika",
            photoUrl = "",
            navController = rememberNavController()
        )
    }
}

@Composable
@Preview(showBackground = true)
fun ScrollToTopButtonPreview() {
    ScrollToTopButton({})
}