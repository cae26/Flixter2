package com.example.flixter2
import android.support.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class Movie (
    @SerializedName("title")
    var title: String? = null,

    @SerializedName("overview")
    var overview: String? = null,

    @SerializedName("poster_path")
    var posterPath : String? = null,

) : java.io.Serializable