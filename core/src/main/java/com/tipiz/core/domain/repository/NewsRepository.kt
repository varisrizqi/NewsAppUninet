package com.tipiz.core.domain.repository

import androidx.paging.PagingData
import com.tipiz.core.domain.model.DataHeadLinesNews
import com.tipiz.core.domain.model.DataSource
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    fun fetchNews(country: String, category: String, q: String, page: Int): Flow<PagingData<DataHeadLinesNews>>
    suspend fun fetchSources(category: String, language: String, country: String): List<DataSource>

}