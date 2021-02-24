package org.curryware.androidsdk.api

import org.curryware.androidsdk.model.AccessOAuthToken
import org.curryware.androidsdk.model.Posts
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

interface RestAPI {

    @GET("posts/1")
    suspend fun getPost(): Response<Posts>

    @POST("/SAAS/auth/oauthtoken")
    suspend fun getAccessToken(): Response<AccessOAuthToken>
}
