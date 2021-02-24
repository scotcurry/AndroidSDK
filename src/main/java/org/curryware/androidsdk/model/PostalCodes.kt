package org.curryware.androidsdk.model

import com.google.gson.annotations.SerializedName

data class PostalCodes(

    @SerializedName("post code")
    val postCode: String,
    val country: String,
    @SerializedName("country abbreviation")
    val abbreviation: String,
    val places: Places
)

data class Places(

    @SerializedName("place name")
    val placeName: String,
    val longitude: String,
    val state: String,
    @SerializedName("state abbreviation")
    val stateAbbreviation: String,
    val latitude: String
)

data class Posts(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)
