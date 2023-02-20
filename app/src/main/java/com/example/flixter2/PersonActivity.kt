package com.example.flixter2

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONException

private const val TAG = "PersonActivity"

class PersonActivity : AppCompatActivity() {
    private lateinit var profileImageView: ImageView
    private lateinit var nameTextView: TextView
    private lateinit var bioTextView: TextView
   // private lateinit var popularityTextView: TextView
    private lateinit var birthDateTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person)
        supportPostponeEnterTransition()
        // add back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Find the views for the screen
        profileImageView = findViewById(R.id.profile_image)
        nameTextView = findViewById(R.id.name)
        bioTextView = findViewById(R.id.biography)
        //popularityTextView = findViewById(R.id.popularity)
        //birthDateTextView = findViewById(R.id.birthday)
        val person = intent.getSerializableExtra(PERSON_EXTRA) as PopularPerson
        val id = person.id

        val details = "https://api.themoviedb.org/3/person/$id?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed"
        // Get the extra from the Intent
        val client = AsyncHttpClient()
        val params = RequestParams()



        //second api call using person id
        client [details, params, object : JsonHttpResponseHandler() {
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e(TAG, "OnFailure $statusCode")
            }

            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                Log.i(TAG, "OnSuccess: JSON data $json")
                try {

                    bioTextView.text = json.jsonObject.getString("biography")



                } catch(e: JSONException) {
                    Log.e(TAG, "Encountered exception $e")
                }
            }
        }]






        // Set the title, description
        nameTextView.text = person.name
        //bioTextView.text = person.biography
        //popularityTextView.text = popularityTextView.text.toString() + "\n" + movie.popularity


        val radius = 30
        Glide.with(this)
            .load(("https://image.tmdb.org/t/p/w500/"+person.profile_path))
            .centerInside()
            .transform(RoundedCorners(radius))
            .into(profileImageView)
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