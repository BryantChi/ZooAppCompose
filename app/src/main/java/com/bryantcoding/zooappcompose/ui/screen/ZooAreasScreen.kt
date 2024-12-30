package com.bryantcoding.zooappcompose.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bryantcoding.zooappcompose.R
import com.bryantcoding.zooappcompose.data.local.entities.ZooAreaEntity
import com.bryantcoding.zooappcompose.ui.components.BaseLoading
import com.bryantcoding.zooappcompose.ui.components.CustomErrorText
import com.bryantcoding.zooappcompose.ui.components.CustomImageWithCoil
import com.bryantcoding.zooappcompose.ui.navgation.Route
import com.bryantcoding.zooappcompose.utils.UiState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ZooAreasScreen(
    navController: NavController,
    zooAreas: UiState<List<ZooAreaEntity>>
) {

    BaseScreen(
        navController = navController,
        title = stringResource(id = R.string.zoo_name)
    ) {
        when (zooAreas) {
            is UiState.Loading -> BaseLoading()
            is UiState.Success -> ShowZooAreas(navController, zooAreas.data)
            is UiState.Error -> CustomErrorText(zooAreas.message)
        }
    }
}

@Composable
fun ShowZooAreas(navController: NavController, data: List<ZooAreaEntity>) {
    LazyColumn {
        items(data, key = { it.id }) { zooArea ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(8.dp)
                    .clickable {
                        navController.navigate(
                            Route.ZooAreaDetailScreen.createRoute(
                                zooArea.id
                            )
                        )
                    },
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 4.dp
                )
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
                            fontWeight = FontWeight.Bold,
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

@Preview
@Composable
fun ZooAreasScreenPreview() {
    ZooAreasScreen(
        navController = rememberNavController(),
        zooAreas = UiState.Success(listOf())
    )
}