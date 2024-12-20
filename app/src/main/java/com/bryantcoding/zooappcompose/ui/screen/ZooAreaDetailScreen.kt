package com.bryantcoding.zooappcompose.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bryantcoding.zooappcompose.R
import com.bryantcoding.zooappcompose.data.local.entities.AnimalEntity
import com.bryantcoding.zooappcompose.data.local.entities.ZooAreaEntity
import com.bryantcoding.zooappcompose.ui.components.BaseLoading
import com.bryantcoding.zooappcompose.ui.components.CustomErrorText
import com.bryantcoding.zooappcompose.ui.components.CustomImageWithCoil
import com.bryantcoding.zooappcompose.ui.components.SingleImageCarousel
import com.bryantcoding.zooappcompose.ui.navgation.Route
import com.bryantcoding.zooappcompose.utils.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ZooAreaDetailScreen(
    navController: NavController,
    zooAreaDetail: UiState<ZooAreaEntity>,
    animalListState: UiState<List<AnimalEntity>>,
) {
    var selectedAnimal by remember { mutableStateOf<AnimalEntity?>(null) }
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true // 跳過部分展開狀態
    )
    val coroutineScope = rememberCoroutineScope()

    BaseScreen(
        navController = navController,
        title = zooAreaDetail.run {
            if (this is UiState.Success) data.eName
                ?: stringResource(id = R.string.zoo_name) else stringResource(id = R.string.zoo_name)
        },
        isBack = true
    ) {
        when (zooAreaDetail) {
            is UiState.Loading -> BaseLoading()
            is UiState.Success -> ShowZooAreasDetail(
                navController,
                zooAreaDetail.data,
                animalListState,
                onAnimalSelected = {
                    selectedAnimal = it
                    showBottomSheet = true
                }
            )

            is UiState.Error -> CustomErrorText(zooAreaDetail.message)
        }
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { coroutineScope.launch { showBottomSheet = false } },
            sheetState = sheetState
        ) {
            selectedAnimal?.let { animal ->
                AnimalBottomSheetContent(animal)
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowZooAreasDetail(
    navController: NavController,
    data: ZooAreaEntity,
    animalListState: UiState<List<AnimalEntity>>,
    onAnimalSelected: (AnimalEntity) -> Unit
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        // 靜態頭部圖片
        item {
            CustomImageWithCoil(
                imageUrl = data.ePicUrl ?: "",
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            Column {
                Text(
                    text = data.eInfo ?: "-",
                    modifier = Modifier
                        .padding(16.dp)
                        .align(alignment = Alignment.CenterHorizontally),
                    style = TextStyle(color = Color.Black, fontSize = 16.sp)
                )
            }
        }

        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                val annotatedString = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Color.Blue, fontSize = 16.sp)) {
                        pushStringAnnotation(tag = "URL", annotation = data.eUrl ?: "")
                        append(stringResource(id = R.string.open_on_web))
                        pop()
                    }
                }

                BasicText(
                    text = annotatedString,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(alignment = Alignment.End)
                        .clickable {
                            // 查找註釋中的 URL 並進行導航
                            val annotations = annotatedString.getStringAnnotations(
                                tag = "URL",
                                start = 0,
                                end = annotatedString.length
                            )
                            annotations
                                .firstOrNull()
                                ?.let { annotation ->
                                    navController.navigate(
                                        Route.WebViewScreen.createRoute(
                                            annotation.item
                                        )
                                    )
                                }
                        }
                )
            }
        }

        // 分隔符
        item {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
                    .background(Color.Gray.copy(alpha = 0.2f))
            )
        }

        // 滾動列表部分
        when (animalListState) {
            is UiState.Success -> {
                animalListState.data.takeIf { it.isNotEmpty() }?.run {
                    items(this, key = { it.id }) { animal ->
                        AnimalItem(animal, navController,
                            onClick = {
                                navController.navigate(
                                    Route.AnimalDetailScreen.createRoute(
                                        animal.id ?: 0
                                    )
                                )
                            },
                            onLongClick = {
                                onAnimalSelected(animal)
                            })
                    }
                } ?: run {
                    item {
                        CustomErrorText(
                            message = stringResource(id = R.string.no_animal),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }

            }

            is UiState.Error -> {
                item {
                    CustomErrorText(animalListState.message)
                }
            }

            is UiState.Loading -> {
                item {
                    BaseLoading()
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AnimalItem(
    animal: AnimalEntity,
    navController: NavController,
    onClick: (() -> Unit)? = null,
    onLongClick: (() -> Unit)? = null
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .combinedClickable(
                onClick = {
                    onClick?.invoke()
                },
                onLongClick = {
                    onLongClick?.invoke()
                },
            ),
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
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.Gray)
            ) {
//                SingleImageCarousel(images = listOf(
//                    animal.pic01Url ?: "",
//                    animal.pic02Url ?: "",
//                    animal.pic03Url ?: "",
//                    animal.pic04Url ?: ""
//                ))
                SingleImageCarousel(
                    images = listOf(
                        "https://www-ws.gov.taipei/001/Upload/432/relpic/10162/9316177/fca1ca06-fef3-4303-b266-44de8c084d6a@710x470.jpg",
                        "https://www-ws.gov.taipei/001/Upload/432/relpic/10162/9313611/a729e20b-33b8-4bfe-a7f3-f1da959386f4@710x470.jpg",
                        "https://www-ws.gov.taipei/001/Upload/432/relpic/10162/9313073/6ea843c7-6591-41e7-9bdf-134bf5187cf9@710x470.jpg",
                        "https://www-ws.gov.taipei/001/Upload/432/relpic/10162/9310472/59fcb6fe-766a-451c-930f-25da7e6e1958@710x470.jpg"
                    )
                )
            }

            Column(
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 16.dp)
                    .weight(1.0f)
            ) {
                Text(
                    text = animal.nameCh ?: "-",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = animal.feature ?: "-",
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

@Composable
fun AnimalBottomSheetContent(animal: AnimalEntity) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(animal.nameCh ?: "-", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            item {
                CustomImageWithCoil(
                    imageUrl = animal.pic01Url ?: "",
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                Column {
                    Text(
                        text = animal.feature ?: stringResource(id = R.string.no_info),
                        modifier = Modifier
                            .padding(16.dp)
                            .align(alignment = Alignment.CenterHorizontally),
                        style = TextStyle(color = Color.Black, fontSize = 16.sp)
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun ZooAreaDetailScreenPreview() {
    ZooAreaDetailScreen(
        navController = rememberNavController(),
        zooAreaDetail = UiState.Success(ZooAreaEntity()),
        animalListState = UiState.Success(listOf())
    )
}