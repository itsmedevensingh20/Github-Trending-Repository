package com.devendra.devendra_demo.retrofit

import com.devendra.devendra_demo.response.RepoSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("search/repositories?sort=stars")
    suspend fun getApiData(
        @Query("q") query: String? = null,
        @Query("page") page: Int? = null,
        @Query("per_page") itemsPerPage: Int? = null
    ): RepoSearchResponse

}