package com.bryantcoding.zooappcompose.ui.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.bryantcoding.zooappcompose.R
import com.bryantcoding.zooappcompose.ui.navgation.Route

@Composable
fun CustomImageWithCoil(
    imageUrl: String,
    modifier: Modifier = Modifier,
    contentDescription: String? = null
) {
    Log.d(Route.ZooAreasScreen::class.simpleName, "zooAreaPic: ${imageUrl}")
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context = LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .placeholder(R.drawable.zoo_placeholder)
            .error(R.drawable.zoo_placeholder)
            .build()
    )
    when (painter.state) {
//        is coil.compose.AsyncImagePainter.State.Loading -> {
//            Box(
//                modifier = Modifier
//                    .padding(16.dp)
//                    .size(50.dp),
//                contentAlignment = Alignment.Center
//            ) {
//                CircularProgressIndicator(
//                    modifier = Modifier.size(20.dp)
//                )
//            }
//        }

//        is coil.compose.AsyncImagePainter.State.Error -> {
//            Box(
//                modifier = Modifier
//                    .padding(16.dp)
//                    .size(50.dp),
//                contentAlignment = Alignment.Center
//            ) {
//                Text(
//                    text = "Image not available",
//                    modifier = Modifier,
//                    textAlign = TextAlign.Center,
//                    style = MaterialTheme.typography.bodySmall,
//                    color = MaterialTheme.colorScheme.error
//                )
//            }
//        }

        else -> {
            Image(
                painter = painter,
                contentDescription = contentDescription,
                modifier = modifier,
                contentScale = ContentScale.Crop
            )
        }
    }
}