package fr.epita.android.action

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat
import fr.epita.android.adapter.GameAdapter
import fr.epita.android.hellogames.GameDetailsActivity
import fr.epita.android.hellogames.Settings
import fr.epita.android.model.Game
import fr.epita.android.model.GameDetails
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GameAction(val context: Context) {
    fun getGameDetailsAction(): Callback<GameDetails> {
        return object : Callback<GameDetails> {
            override fun onFailure(call: Call<GameDetails>, t: Throwable) {
                Log.w("TAG", t.message.toString())
            }
            override fun onResponse(call: Call<GameDetails>, response: Response<GameDetails>) {
                if (response.code() == 200) {
                    val responseData = response.body()
                    if (responseData != null) {
                        Log.d("TAG", "Game with id ${responseData.id} loaded")
                        val activity = Intent(context, GameDetailsActivity::class.java)
                        activity.putExtra("Game", responseData)
                        ContextCompat.startActivity(context, activity, null)
                    } else {
                        Log.w("TAG", "Empty response body")
                    }
                } else {
                    Log.w("TAG", response.message())
                }
            }
        }
    }

    fun getGamesListAction(games: ArrayList<Game>, adapter: GameAdapter, getItem: (games: List<Game>) -> Game) : Callback<List<Game>> {
        return object : Callback<List<Game>> {
            override fun onFailure(call: Call<List<Game>>, t: Throwable) {
                Log.w("TAG", t.message.toString())
            }

            override fun onResponse(call: Call<List<Game>>, response: Response<List<Game>>) {
                if (response.code() == 200) {
                    val responseData = response.body()
                    if (responseData != null) {
                        Log.w("TAG", "Get " + responseData.size + " games")
                        games.clear()
                        if (Settings.Display.SHOW_ALL_GAMES) {
                            games.addAll(responseData)
                        } else {
                            for (i in 0 until Settings.Display.SHOW_COUNT_GAMES) {
                                var g: Game? = null
                                while (g == null || games.contains(g)) {
                                    g = getItem(responseData)
                                }
                                games.add(g)
                            }
                        }
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }
}