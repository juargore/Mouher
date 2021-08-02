package com.glass.domain.entities


// Data class to get/match the info from Server
// It is filled on MallData (fun getLobbyData())
data class LobbyFullData(
    val title: String? = null,
    val description: String? = null,
    val listItemsLobby: List<ItemLobby>? = null
)


// Data class used on UI
data class ItemLobby(
    var urlImage: String? = null,
    val title: String? = null,
    val subtitle: String? = null,
    val linkToOpen: String? = null
)