package fr.epita.android.hellogames

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.epita.android.common.HttpClient
import fr.epita.android.model.Game
import fr.epita.android.adapter.GameAdapter
import fr.epita.android.webService.WebServiceInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class Settings {
    class Display {
        companion object {
            val SHOW_ALL_GAMES = true
            val SHOW_COUNT_GAMES = 4
        }
    }
}

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private val games: ArrayList<Game> = ArrayList()

    private fun getRandomGame(list: List<Game>): Game {
        val rand = Random()
        return list[rand.nextInt(list.size)]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ImageView(this@MainActivity)

        viewManager = LinearLayoutManager(this)
        viewAdapter =
            GameAdapter(this@MainActivity, games)

        recyclerView = findViewById<RecyclerView>(R.id.activity_main_list_games).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        val wsCallback: Callback<List<Game>> = object : Callback<List<Game>> {
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
                                    g = getRandomGame(responseData)
                                }
                                games.add(g)
                            }
                        }
                        viewAdapter.notifyDataSetChanged()
                    }
                }
            }
        }

        val http = HttpClient<WebServiceInterface>(
            "https://androidlessonsapi.herokuapp.com/api/",
            WebServiceInterface::class.java
        )
        http.getService().getAll().enqueue(wsCallback)
    }
}
