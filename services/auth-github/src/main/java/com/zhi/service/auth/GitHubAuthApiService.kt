/*
 * Copyright [2018] [zhi]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zhi.service.auth

import com.google.gson.annotations.SerializedName
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Url

interface GitHubAuthTokenService {
    @POST
    fun getAuthToken(
        @Url url: String = "https://github.com/login/oauth/access_token",
        @Query("client_id") clientId: String,
        @Query("client_secret") clientSecret: String,
        @Query("code") code: String,
        @Header("Accept") header: String = "application/json"
    ): Observable<GitHubAuthToken>
}

interface GitHubAuthApiService {
    @GET
    fun getAuthUser(
        @Url url: String = "https://api.github.com/user",
        @Header("Authorization") token: String
    ): Observable<GitHubAuthUser>
}

data class GitHubAuthToken(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("token_type") val tokenType: String,
    @SerializedName("scope") val scope: String
)

data class GitHubAuthUser(
    @SerializedName("email") val email: String,
    @SerializedName("name") val name: String,
    @SerializedName("avatar_url") val avatar: String
) : AuthUser {
    override val providerId: String
        get() = GitHubAuthProvider.providerId
}