package com.tipiz.newsappuninet.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.tipiz.core.domain.model.CategoryModel
import com.tipiz.core.domain.model.DataSource
import com.tipiz.core.domain.model.QueryParams
import com.tipiz.core.domain.repository.NewsRepository
import com.tipiz.core.utils.state.UiState
import com.tipiz.core.utils.state.asMutableStateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: NewsRepository) : ViewModel() {

    val category by lazy { MutableLiveData<String>() }
    var q = ""

    private val _query = MutableLiveData(QueryParams(country = "us", category = category.value ?: "", q = q))
    val query: LiveData<QueryParams> = _query

    init {
        category.value = ""
    }

    val news = query.switchMap { queryParams ->
        repository.fetchNews(
            country = queryParams.country,
            category = category.value ?: "",
            q = q,
            page = 1
        ).cachedIn(viewModelScope).asLiveData()
    }

    private val _responseSource: MutableStateFlow<UiState<List<DataSource>>> =
        MutableStateFlow((UiState.Empty))
    val responseSource = _responseSource.asStateFlow()

    fun fetchSource() {
        viewModelScope.launch {
            _responseSource.asMutableStateFlow {
                repository.fetchSources(category = category.value ?: "" , language = "en", country = "us")
            }
        }
    }
    val categories = listOf(
        CategoryModel("", "Headlines"),
        CategoryModel("Business", "Business"),
        CategoryModel("Entertainment", "Entertainment"),
        CategoryModel("General", "General"),
        CategoryModel("Health", "Health"),
        CategoryModel("Science", "Science"),
        CategoryModel("Sports", "Sports"),
        CategoryModel("Technology", "Technology")
    )

    fun updateCategory(newCategory: String) {
        category.value = newCategory
        _query.value = QueryParams(country = "us", category = newCategory, q = q)
    }

    fun updateSearchQuery(newQuery: String) {
        q = newQuery
        _query.value = QueryParams(country = "us", category = category.value ?: "", q = newQuery)
    }

}