package fr.epita.android.webService

import fr.epita.android.model.Game
import fr.epita.android.model.GameDetails
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServiceInterface {
    @GET("game/list")
    fun getAll(): Call<List<Game>>

    @GET("game/details")
    fun getOne(@Query("game_id") userId: Int): Call<GameDetails>
}
