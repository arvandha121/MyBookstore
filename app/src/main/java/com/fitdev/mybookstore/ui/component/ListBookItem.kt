package com.fitdev.mybookstore.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.fitdev.mybookstore.ui.navigate.Screen
import com.fitdev.mybookstore.ui.theme.MyBookstoreTheme

@Composable
fun ListBookItem(
    id: Int,
    name: String,
    photoUrl: String,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clickable {
            navController.navigate(Screen.Detail.createRoute(id)) {
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop = true
            }
        }
    ) {
        val painter = rememberImagePainter(
            data = photoUrl,
            builder = {
                crossfade(true)
            }
        )

        Image(
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(8.dp)
                .size(60.dp)
                .clip(CircleShape)
        )

        Text(
            text = name,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(start = 6.dp, end = 16.dp)
        )
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