package com.example.flixter2

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners


const val MOVIE_EXTRA = "MOVIE_EXTRA"
private const val TAG = "MovieAdapter"

class MovieAdapter(private val movies: List<Movie>) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        ViewCompat.setTransitionName(holder.itemView.findViewById(R.id.movie_image), movie.title)
        holder.bind(movie)
    }

    override fun getItemCount() = movies.size

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        private val movieTitle = itemView.findViewById(R.id.title) as TextView
        private val movieImage = itemView.findViewById(R.id.movie_image) as ImageView

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(movie: Movie) {
            movieTitle.text = movie.title

            val radius = 20
            Glide.with(itemView.context)
                .load(("https://image.tmdb.org/t/p/w500/"+movie.posterPath))
                .centerInside()
                .transform(RoundedCorners(radius))
                .into(movieImage)
        }

        override fun toString(): String {
            return movieTitle.toString()
        }

        override fun onClick(v: View?) {
            val movie = movies[absoluteAdapterPosition]


            val intent = Intent(itemView.context, MovieActivity::class.java)
            intent.putExtra(MOVIE_EXTRA, movie)

            itemView.context.startActivity(intent)
        }
    }
}