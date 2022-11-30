package com.devendra.devendra_demo

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.devendra.devendra_demo.response.ListResponse
import com.devendra.devendra_demo.retrofit.ApiInterface
import javax.inject.Inject
import javax.inject.Singleton


class MainApiRepository @Inject constructor(
    private val apiInterface: ApiInterface
) {
    fun getApiData(
        query: String
    ): LiveData<PagingData<ListResponse>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                maxSize = 100,
                enablePlaceholders = false
            ), pagingSourceFactory = { ApiPagingSource(apiInterface, query) }
        ).liveData
    }


}