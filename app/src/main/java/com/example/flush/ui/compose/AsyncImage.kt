package com.example.flush.ui.compose

import android.net.Uri
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImagePainter.State.Empty.painter
import coil.compose.rememberAsyncImagePainter

@Composable
fun AsyncImage(
    uri: Uri,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
) {
    androidx.compose.foundation.Image(
        painter = rememberAsyncImagePainter(uri),
        contentDescription = null,
        modifier = modifier
            .clip(MaterialTheme.shapes.small),
        contentScale = contentScale,
    )
}