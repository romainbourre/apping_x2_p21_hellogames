package fr.epita.android.model

import java.io.Serializable

data class Game(
    val id: Int,
    val name: String,
    val picture: String
) : Serializable