package fr.epita.android.hellogames

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import fr.epita.android.model.GameDetails
import kotlinx.android.synthetic.main.game_details_layout.*

class GameDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_details_layout)

        val game: GameDetails = intent.getSerializableExtra("Game") as GameDetails
        Glide.with(this).load(game.picture).into(game_picture)
        game_name.text = game.name
        game_type.text = game.type
        game_players.text = game.players.toString()
        game_year.text = game.year.toString()
        game_description.text = game.description_en
    }
}