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

class PersonFragment(parentContext: Context) : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_person_list, container, false)
        val progressBar = view.findViewById<View>(R.id.progress) as ContentLoadingProgressBar
        val recyclerView = view.findViewById<View>(R.id.popular_people) as RecyclerView
        val context = view.context
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        updateAdapter(progressBar, recyclerView)
        return view
    }


    private fun updateAdapter(progressBar: ContentLoadingProgressBar, recyclerView: RecyclerView) {
        progressBar.show()


        val client = AsyncHttpClient()
        val params = RequestParams()
        params["api-key"] = API_KEY
        //
        // Using the client, perform the HTTP request
        client[
                "https://api.themoviedb.org/3/person/popular?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed",
                params,
                object : JsonHttpResponseHandler()
                {
                    /*
                     * The onSuccess function gets called when
                     * HTTP response status is "200 OK"
                     */
                    override fun onSuccess(
                        statusCode: Int,
                        headers: Headers,
                        json: JsonHttpResponseHandler.JSON
                    ) {
                        // The wait for a response is over
                        progressBar.hide()

                        //Parse JSON into Models
//                        val resultsJSON : JSONObject = json.jsonObject.get("results") as JSONObject
                        val peoplesRawJSON : String = json.jsonObject.get("results").toString()

                        // Create Gson object to parse JSON into model
                        val gson = Gson()
                        val arrayPersonType = object : TypeToken<List<PopularPerson>>() {}.type

                        var models : List<PopularPerson> = gson.fromJson(peoplesRawJSON, arrayPersonType)
                        recyclerView.adapter = PersonAdapter(models)

                        // Look for this in Logcat:
                        Log.d("PopularPeopleFragment", "response successful")
                        Log.d("json format", json.toString())
                        // See JSON response
                        //Log.d("JSONresponse", json.toString())
                    }


                    override fun onFailure(
                        statusCode: Int,
                        headers: Headers?,
                        errorResponse: String,
                        t: Throwable?
                    ) {

                        progressBar.hide()


                        t?.message?.let {
                            Log.e("PopularPeopleFragment", errorResponse)
                        }
                    }
                }]

    }
}