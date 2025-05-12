package com.tipiz.core.utils

import com.tipiz.core.data.network.data.headlinenews.HeadLinesNewsResponse
import com.tipiz.core.data.network.data.source.Source
import com.tipiz.core.domain.model.DataHeadLinesNews
import com.tipiz.core.domain.model.DataSource

class DataMapper {

    fun mapHeadLinesNew(
        response: HeadLinesNewsResponse
    ): List<DataHeadLinesNews> = response.articles?.map { article ->
        DataHeadLinesNews(
            author = article?.author.orEmpty(),
            content = article?.content.orEmpty(),
            description = article?.description.orEmpty(),
            publishedAt = article?.publishedAt.orEmpty(),
            source = mapDataSource(article?.source),
            title = article?.title.orEmpty(),
            url = article?.url.orEmpty(),
            urlToImage = article?.urlToImage.orEmpty()
        )
    }.orEmpty()

    fun mapListDataSource(
        response: List<Source?>?
    ): List<DataSource> = response?.map { source -> mapDataSource(source) }.orEmpty()

    fun mapDataSource(source: Source?): DataSource = DataSource(
        category = source?.category.orEmpty(),
        country = source?.country.orEmpty(),
        description = source?.description.orEmpty(),
        id = source?.id.orEmpty(),
        language = source?.language.orEmpty(),
        name = source?.name.orEmpty(),
        url = source?.url.orEmpty()
    )
}
