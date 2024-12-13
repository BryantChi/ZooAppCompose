package com.bryantcoding.zooappcompose.ui.navgation

sealed class Route(
    val route: String
) {
    data object ZooAreasScreen : Route("zoo_areas")
    data object ZooAreaDetailScreen : Route("zoo_area_detail/{id}") {
        fun createRoute(id: Int) = "zoo_area_detail/$id"
    }
    data object WebViewScreen : Route("web_view/{url}") {
        fun createRoute(url: String) = "web_view/$url"
    }
}
