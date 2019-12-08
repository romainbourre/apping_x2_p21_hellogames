package fr.epita.android.webService

import fr.epita.android.model.Game
import retrofit2.Call
import retrofit2.http.GET

interface WebServiceInterface {
    @GET("game/list")
    fun getAll(): Call<List<Game>>
}
