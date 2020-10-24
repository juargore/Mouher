package com.glass.domain.entities

data class LobbyData(
    val title: String? = null,
    val description: String? = null,
    val listItemsLobby: List<ItemLobby>? = null
)


data class ItemLobby(
    var urlImage: String? = null,
    val title: String? = null,
    val subtitle: String? = null
)