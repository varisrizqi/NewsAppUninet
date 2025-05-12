package com.tipiz.core.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tipiz.core.BuildConfig
import com.tipiz.core.data.network.retrofit.ApiService
import com.tipiz.core.domain.model.DataHeadLinesNews
import com.tipiz.core.utils.DataMapper

class NewsPagingSource(
    private val api: ApiService,
    private val category: String,
    private val country: String,
    private val q: String,
    private val dataMapper: DataMapper
) : PagingSource<Int, DataHeadLinesNews>() {
    override fun getRefreshKey(state: PagingState<Int, DataHeadLinesNews>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataHeadLinesNews> {
        return try {
            val position = params.key ?: 1
            val responseData = api.fetchNews(
                apiKey = BuildConfig.API_KEY,
                category = category,
                country = country,
                page = position,
                q = q
            )

            val articles = responseData.articles?.let { dataMapper.mapHeadLinesNew(responseData) }
                ?: emptyList()
            val nextKey = if (position * 10 >= (responseData.totalResults ?: 0)) {
                null
            } else {
                position + 1
            }

            LoadResult.Page(
                data = articles,
                prevKey = null,
                nextKey = nextKey
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }

    }
}