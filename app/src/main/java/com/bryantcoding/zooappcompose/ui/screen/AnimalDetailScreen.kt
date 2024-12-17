package com.bryantcoding.zooappcompose.ui.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bryantcoding.zooappcompose.R
import com.bryantcoding.zooappcompose.data.local.entities.AnimalEntity
import com.bryantcoding.zooappcompose.ui.components.BaseLoading
import com.bryantcoding.zooappcompose.ui.components.CustomErrorText
import com.bryantcoding.zooappcompose.ui.components.CustomImageWithCoil
import com.bryantcoding.zooappcompose.ui.viewmodel.AnimalViewModel
import com.bryantcoding.zooappcompose.utils.DateUtils.formatDateTimeToDate
import com.bryantcoding.zooappcompose.utils.UiState

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AnimalDetailScreen(
    navController: NavController,
    viewModel: AnimalViewModel
) {
    val animalDetail by viewModel.animal.collectAsState()
    val id = navController.currentBackStackEntry?.arguments?.getString("id")

    id?.run {
        LaunchedEffect(id) {
            viewModel.fetchAnimalDetail(id.toInt())
        }
    }

    BaseScreen(
        navController = navController,
        title = animalDetail.run {
            if (this is UiState.Success) data.nameCh
                ?: stringResource(id = R.string.zoo_name) else stringResource(id = R.string.zoo_name)
        },
        isBack = true
    ) {
        when (val state = animalDetail) {
            is UiState.Loading -> BaseLoading()
            is UiState.Success -> ShowAnimalDetail(state.data)
            is UiState.Error -> CustomErrorText(state.message)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ShowAnimalDetail(animal: AnimalEntity) {
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
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text(
                    text = animal.nameCh ?: "",
                    style = TextStyle(color = Color.Black, fontSize = 18.sp)
                )
                Text(
                    text = animal.nameEn ?: "",
                    style = TextStyle(color = Color.Black, fontSize = 16.sp)
                )

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(16.dp)
                )

                Text(
                    text = stringResource(id = R.string.distribution),
                    style = TextStyle(color = Color.Black, fontSize = 16.sp)
                )
                Text(
                    text = animal.distribution ?: "",
                    style = TextStyle(color = Color.Black, fontSize = 16.sp)
                )

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(16.dp)
                )

                Text(
                    text = stringResource(id = R.string.habitat),
                    style = TextStyle(color = Color.Black, fontSize = 16.sp)
                )
                Text(
                    text = animal.habitat ?: "",
                    style = TextStyle(color = Color.Black, fontSize = 16.sp)
                )

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(16.dp)
                )

                Text(
                    text = stringResource(id = R.string.feature),
                    style = TextStyle(color = Color.Black, fontSize = 16.sp)
                )
                Text(
                    text = animal.feature ?: "",
                    style = TextStyle(color = Color.Black, fontSize = 16.sp)
                )

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(16.dp)
                )

                Text(
                    text = stringResource(id = R.string.behavior),
                    style = TextStyle(color = Color.Black, fontSize = 16.sp)
                )
                Text(
                    text = animal.behavior ?: "",
                    style = TextStyle(color = Color.Black, fontSize = 16.sp)
                )

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(16.dp)
                )

                Text(
                    text = stringResource(id = R.string.diet),
                    style = TextStyle(color = Color.Black, fontSize = 16.sp)
                )
                Text(
                    text = animal.diet ?: "",
                    style = TextStyle(color = Color.Black, fontSize = 16.sp)
                )

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(16.dp)
                )

                Text(
                    text = "${stringResource(id = R.string.import_date)} ${formatDateTimeToDate(animal.importDate ?: "")}",
                    style = TextStyle(color = Color.Black, fontSize = 16.sp)
                )

            }
        }
    }
}