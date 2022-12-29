package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class InfoKotlin(
    val id: Int,
    val title: String,
    val image: String,
    val about: String,
    val tags : List<String>,
    val ranking: Double,
    val yearRelease: Int,
    val difficulty : String
)
