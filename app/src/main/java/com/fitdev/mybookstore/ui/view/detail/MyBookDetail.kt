package com.fitdev.mybookstore.ui.view.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.fitdev.mybookstore.R
import com.fitdev.mybookstore.data.Book
import com.fitdev.mybookstore.data.BooksData
import com.fitdev.mybookstore.ui.theme.MyBookstoreTheme

@Composable
fun MyBookDetail(
    id: Int,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
) {
    val localContext = LocalContext.current

    val selected = remember {
        mutableStateOf<Book?>(null)
    }

    selected.value = BooksData.books.find { it.id == id }

    selected.value?.let {
        LazyColumn(modifier = modifier.padding(16.dp)) {
            item {
                val imageRequest = remember(localContext) {
                    ImageRequest.Builder(localContext)
                        .data(it.photoUrl)
                        .build()
                }
                Row {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(id = R.string.detail),
                        modifier = modifier
                            .padding(top = 16.dp, bottom = 16.dp, end = 8.dp)
                            .height(30.dp)
                            .clickable { onBackClick() }
                    )
                    Text(
                        text = stringResource(id = R.string.detail),
                        style = MaterialTheme.typography.titleLarge,
                        modifier = modifier
                            .padding(start = 8.dp, top = 16.dp, end = 8.dp),
                        fontWeight = FontWeight.Bold
                    )
                }
                Box {
                    Image(
                        painter = rememberAsyncImagePainter(model = imageRequest),
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(550.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
                }
                Spacer(modifier = modifier.height(16.dp))
                Text(
                    text = it.name,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = modifier.height(16.dp))
                Text(
                    text = it.description,
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(id = R.string.price),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = it.price,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(modifier = modifier.height(32.dp))
            }
        }
    }
}

@Composable
@Preview(showBackground = true, device = Devices.PIXEL_4)
fun DetailBookItemPreview() {
    MyBookstoreTheme() {
        MyBookDetail(
            id = BooksData.books.first().id,
            onBackClick = {}
        )
    }
}

