package com.example.flixter2
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Headers

private const val API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed"

class PopularMoviesFragment(parentContext: Context) : Fragment(){
    /*
     * Constructing the view
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movies_list, container, false)
        val progressBar = view.findViewById<View>(R.id.progress) as ContentLoadingProgressBar
        val recyclerView = view.findViewById<View>(R.id.movies) as RecyclerView
        val context = view.context
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        updateAdapter(progressBar, recyclerView)
        return view
    }

    /*
         * Updates the RecyclerView adapter with new data.  This is where the
         * networking magic happens!
         */
    private fun updateAdapter(progressBar: ContentLoadingProgressBar, recyclerView: RecyclerView) {
        progressBar.show()

        // Create and set up an AsyncHTTPClient() here
        val client = AsyncHttpClient()
        val params = RequestParams()
        params["api-key"] = API_KEY
        //
        // Using the client, perform the HTTP request
        client[
                "https://api.themoviedb.org/3/movie/popular?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed",
                params,
                object : JsonHttpResponseHandler()
                {

                    override fun onSuccess(
                        statusCode: Int,
                        headers: Headers,
                        json: JsonHttpResponseHandler.JSON
                    ) {
                        // The wait for a response is over
                        progressBar.hide()


                        val moviesRawJSON : String = json.jsonObject.get("results").toString()

                        // Create Gson object to parse JSON into model
                        val gson = Gson()
                        val arrayMovieType = object : TypeToken<List<Movie>>() {}.type

                        var models : List<Movie> = gson.fromJson(moviesRawJSON, arrayMovieType)
                        recyclerView.adapter = MovieAdapter(models)

                        // Look for this in Logcat:
                        Log.d("PopularMoviesFragment", "response successful")


                    }


                    override fun onFailure(
                        statusCode: Int,
                        headers: Headers?,
                        errorResponse: String,
                        t: Throwable?
                    ) {
                        // The wait for a response is over
                        progressBar.hide()


                        t?.message?.let {
                            Log.e("PopularMoviesFragment", errorResponse)
                        }
                    }
                }]

    }
}