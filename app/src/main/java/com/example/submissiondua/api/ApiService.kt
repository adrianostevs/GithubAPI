package com.example.submissiondua.api
import com.example.submissiondua.response.DetailUserResponse
import com.example.submissiondua.response.FollowersResponseItem
import com.example.submissiondua.response.FollowingResponseItem
import com.example.submissiondua.response.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @Headers("Authorization: token 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")
    @GET("search/users")
    fun getUser(
        @Query("q") q: String
    ): Call<UserResponse>

    @Headers("Authorization: token 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")
    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @Headers("Authorization: token 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")
    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<FollowingResponseItem>>

    @Headers("Authorization: token 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")
    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<FollowersResponseItem>>

}