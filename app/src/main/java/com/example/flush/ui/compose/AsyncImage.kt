package com.example.flush.ui.compose

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImagePainter.State.Empty.painter
import coil.compose.rememberAsyncImagePainter

@Composable
fun AsyncImage(
    uri: String,
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.small,
    contentScale: ContentScale = ContentScale.Crop,
) {
    androidx.compose.foundation.Image(
        painter = rememberAsyncImagePainter(uri),
        contentDescription = null,
        modifier = modifier
            .clip(shape),
        contentScale = contentScale,
    )
}
