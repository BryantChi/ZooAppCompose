package com.bryantcoding.zooappcompose.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bryantcoding.zooappcompose.R
import com.bryantcoding.zooappcompose.data.local.entities.AnimalEntity
import com.bryantcoding.zooappcompose.ui.components.BaseLoading
import com.bryantcoding.zooappcompose.ui.components.CustomErrorText
import com.bryantcoding.zooappcompose.ui.components.CustomImageWithCoil
import com.bryantcoding.zooappcompose.utils.DateUtils.formatDateTimeToDate
import com.bryantcoding.zooappcompose.utils.UiState

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AnimalDetailScreen(
    navController: NavController,
    animalDetail: UiState<AnimalEntity>,
) {
    BaseScreen(
        navController = navController,
        title = animalDetail.run {
            if (this is UiState.Success) data.nameCh
                ?: stringResource(id = R.string.zoo_name) else stringResource(id = R.string.zoo_name)
        },
        isBack = true
    ) {
        when (animalDetail) {
            is UiState.Loading -> BaseLoading()
            is UiState.Success -> ShowAnimalDetail(animalDetail.data)
            is UiState.Error -> CustomErrorText(animalDetail.message)
        }
    }
}

@Composable
fun ShowAnimalDetail(animal: AnimalEntity) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .verticalScroll(scrollState)
    ) {
        CustomImageWithCoil(
            imageUrl = animal.pic01Url ?: "",
            modifier = Modifier.fillMaxWidth()
        )

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
                text = "${stringResource(id = R.string.import_date)} ${
                    (animal.importDate ?: "").formatDateTimeToDate()
                }",
                style = TextStyle(color = Color.Black, fontSize = 16.sp)
            )

        }
    }
}

@Preview
@Composable
fun AnimalDetailPreview() {
    AnimalDetailScreen(
        navController = rememberNavController(),
        animalDetail = UiState.Success(AnimalEntity())
    )
}