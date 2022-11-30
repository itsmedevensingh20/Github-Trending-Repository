package com.devendra.devendra_demo

import androidx.paging.PagingSource
import com.devendra.devendra_demo.response.ListResponse
import com.devendra.devendra_demo.retrofit.ApiInterface
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

class ApiPagingSource(
    private val apiInterface: ApiInterface,
    private val query: String? = null
) : PagingSource<Int, ListResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListResponse> {
        val position = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = apiInterface.getApiData(query, position, params.loadSize)
            val res = response.items

            LoadResult.Page(
                data = res,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (res.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}