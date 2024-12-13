package com.bryantcoding.zooappcompose.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun CustomErrorText(
    message: String,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
    textAlign: TextAlign? = null
) {
    Text(
        text = message,
        modifier = modifier,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.error,
        textAlign = textAlign
    )
}