package fr.epita.android.hellogames

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.epita.android.action.GameAction
import fr.epita.android.common.HttpClient
import fr.epita.android.model.Game
import fr.epita.android.adapter.GameAdapter
import fr.epita.android.webService.WebServiceInterface
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

    val http = HttpClient<WebServiceInterface>(
        "https://androidlessonsapi.herokuapp.com/api/",
        WebServiceInterface::class.java
    )

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
            GameAdapter(this@MainActivity, games, http.getService(),
                GameAction(this@MainActivity).getGameDetailsAction())

        recyclerView = findViewById<RecyclerView>(R.id.activity_main_list_games).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        http.getService().getAll().enqueue(GameAction(this@MainActivity).getGamesListAction(games,
            viewAdapter as GameAdapter, ::getRandomGame))
    }
}
