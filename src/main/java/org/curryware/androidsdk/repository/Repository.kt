package org.curryware.androidsdk.repository

import org.curryware.androidsdk.api.RetrofitInstance
import org.curryware.androidsdk.model.AccessOAuthToken
import org.curryware.androidsdk.model.Posts
import retrofit2.Response

class Repository {

    suspend fun getPost(): Response<Posts> {

        return RetrofitInstance.api.getPost()
    }

    suspend fun getAccessToken(): Response<AccessOAuthToken> {

        return RetrofitInstance.api.getAccessToken()
    }
}