package com.bryantcoding.zooappcompose.ui.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.bryantcoding.zooappcompose.R

@Composable
fun CustomImageWithCoil(
    imageUrl: String,
    modifier: Modifier = Modifier,
    contentDescription: String? = null
) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context = LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .placeholder(R.drawable.zoo_placeholder)
            .error(R.drawable.zoo_placeholder)
            .build()
    )
    Image(
        painter = painter,
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = ContentScale.Crop
    )
}