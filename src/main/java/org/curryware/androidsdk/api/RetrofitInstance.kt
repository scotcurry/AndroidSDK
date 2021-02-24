package org.curryware.androidsdk.api

import org.curryware.androidsdk.model.AccessOAuthToken
import org.curryware.androidsdk.util.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val retrofit by lazy {

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: RestAPI by lazy {
        retrofit.create(RestAPI::class.java)
    }

    val getToken: AccessOAuthToken by lazy {
        retrofit.create(AccessOAuthToken::class.java)
    }
}