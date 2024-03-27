package com.ocean.domain.entities


data class CountryStateData(
    val Error: Int? = null,
    val Mensaje: String? = null,
    val Paises: List<Country>? = null,
    val Estados: List<State>? = null
)

data class Country(
    val IdPais: Int? = null,
    val Nombre: String? = null
)

data class State(
    val IdEstado: Int? = null,
    val Nombre: String? = null
)