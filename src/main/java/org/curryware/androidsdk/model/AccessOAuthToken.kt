package org.curryware.androidsdk.model

data class AccessOAuthToken (

        val scope: String,
        val access_token: String,
        val token_type: String,
        val expires_in: Int,
        val refresh_token: String
)