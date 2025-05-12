package com.tipiz.core.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.tipiz.core.BuildConfig
import com.tipiz.core.data.datasource.NewsPagingSource
import com.tipiz.core.data.network.retrofit.ApiService
import com.tipiz.core.domain.model.DataHeadLinesNews
import com.tipiz.core.domain.model.DataSource
import com.tipiz.core.utils.DataMapper
import com.tipiz.core.utils.state.safeApiCall
import kotlinx.coroutines.flow.Flow

class NewsRepositoryImpl(
    private val apiEndpoint: ApiService,
    private val mapper: DataMapper,
) : NewsRepository {
    override fun fetchNews(
        country: String,
        category: String,
        q: String,
        page: Int
    ): Flow<PagingData<DataHeadLinesNews>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                prefetchDistance = 1,
                initialLoadSize = 10
            ),
            pagingSourceFactory = {
                NewsPagingSource(
                    category = category,
                    country = country,
                    q = q,
                    api = apiEndpoint,
                    dataMapper = mapper
                )
            }

        ).flow
    }

    override suspend fun fetchSources(
        category: String,
        language: String,
        country: String
    ): List<DataSource> {
        return safeApiCall {
            val response = apiEndpoint.fetchSources(
                apiKey = BuildConfig.API_KEY,
                category = category,
                language = language,
                country = country
            )
            mapper.mapListDataSource(response.sources)
        }
    }
}