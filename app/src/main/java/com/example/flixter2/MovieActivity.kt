package com.example.flixter2
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

private const val TAG = "MovieActivity"

class MovieActivity : AppCompatActivity() {
    private lateinit var movieImageView: ImageView
    private lateinit var titleTextView: TextView
    private lateinit var overviewTextView: TextView
    private lateinit var popularityTextView: TextView
    private lateinit var releaseDateTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)
        supportPostponeEnterTransition()
        // add back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        movieImageView = findViewById(R.id.movie_image)
        titleTextView = findViewById(R.id.title)
        overviewTextView = findViewById(R.id.overview)

        val movie = intent.getSerializableExtra(MOVIE_EXTRA) as Movie


        titleTextView.text = movie.title
        overviewTextView.text = movie.overview

        ViewCompat.setTransitionName(movieImageView, movie.title)


        val radius = 30
        Glide.with(this)
            .load(("https://image.tmdb.org/t/p/w500/"+movie.posterPath))
            .centerInside()
            .transform(RoundedCorners(radius))
            .into(movieImageView)
        startPostponedEnterTransition()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            supportFinishAfterTransition()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}