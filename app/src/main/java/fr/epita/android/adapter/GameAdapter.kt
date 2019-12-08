package fr.epita.android.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.epita.android.model.Game
import fr.epita.android.hellogames.R

class GameAdapter(private val context: Context, private val games: List<Game>) : RecyclerView.Adapter<GameAdapter.ViewHolder>() {
    class ViewHolder(rowView: View): RecyclerView.ViewHolder(rowView) {
        val name: TextView = rowView.findViewById(R.id.name)
        val picture: ImageView = rowView.findViewById(R.id.logo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rowView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(rowView)
    }

    override fun getItemCount(): Int {
        return games.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val game = this.games[position]
        holder.name.text = game.name
        Glide.with(this.context).load(game.picture).into(holder.picture)
        holder.itemView.setOnClickListener{v: View? ->
            if (v != null) {
                Log.d("TAG", "Clicked on element with $position from list")
            }
        }
    }
}