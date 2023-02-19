package com.example.flixter2


import android.support.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Keep
@Serializable
data class PopularPerson (

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("biography")
    var biography: String? = null,

    @SerializedName("profile_path")
    var profile_path : String? = null,

    @SerializedName("id")
    var id : String? = null



): java.io.Serializable