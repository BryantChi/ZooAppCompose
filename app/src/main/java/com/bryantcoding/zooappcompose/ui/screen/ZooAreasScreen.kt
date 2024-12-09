package com.bryantcoding.zooappcompose.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bryantcoding.zooappcompose.R
import com.bryantcoding.zooappcompose.data.model.ZooAreaResponse
import com.bryantcoding.zooappcompose.ui.components.BasePageLoading
import com.bryantcoding.zooappcompose.ui.components.CustomImageWithCoil
import com.bryantcoding.zooappcompose.ui.components.CustomTopBar
import com.bryantcoding.zooappcompose.viewmodel.UiState
import com.bryantcoding.zooappcompose.viewmodel.ZooAreaViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ZooAreasScreen(
    viewModel: ZooAreaViewModel
) {
    val zooAreas by viewModel.zooInfoStatus.collectAsState()

    if (zooAreas !is UiState.Success) {
        LaunchedEffect(Unit) {
            viewModel.fetchZooAreas()
        }
    }

    Scaffold(
        topBar = {
            CustomTopBar(
                title = stringResource(id = R.string.zoo_name),
                showBackButton = true
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (val state = zooAreas) {
                is UiState.Loading -> ShowLoading()
                is UiState.Success -> ShowZooAreas(state.data)
                is UiState.Error -> ShowError(state.message)
            }
        }
    }


}

@Composable
fun ShowError(message: String) {
    Text(
        text = message,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.error
    )
}

@Composable
fun ShowZooAreas(data: List<ZooAreaResponse.ZooArea>) {
    LazyColumn {
        items(data, key = { it.id ?: 0 }) { zooArea ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(8.dp)
//                    .clickable { navController.navigate("zoo_area_detail/${zooArea.id}") }
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.onSecondary),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CustomImageWithCoil(
                        imageUrl = zooArea.ePicUrl ?: "",
                        modifier = Modifier.size(100.dp)
                    )

                    Column(
                        modifier = Modifier
                            .padding(vertical = 8.dp, horizontal = 16.dp)
                            .weight(1.0f)
                    ) {
                        Text(
                            text = zooArea.eName ?: "-",
                            style = MaterialTheme.typography.titleMedium,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = zooArea.eInfo ?: "-",
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .size(32.dp)
                            .padding(end = 8.dp)
                    )

                }
            }
        }
    }
}

@Composable
fun ShowLoading() {
    BasePageLoading()
}
