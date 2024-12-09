package com.bryantcoding.zooappcompose.ui.navgation

sealed class Route(
    val route: String
) {
    object ZooAreasScreen : Route("zoo_areas")
    object ZooAreaDetailScreen : Route("zoo_area_detail/{id}")
}
