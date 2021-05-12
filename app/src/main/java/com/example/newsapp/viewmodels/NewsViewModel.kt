package com.example.newsapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.models.Article
import com.example.newsapp.models.NewsResponse
import com.example.newsapp.repository.NewsRepository
import com.example.newsapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(private val newsRepository: NewsRepository): ViewModel() {

    val newsHeadlines: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val headlinesPageNews = 1

    val searchedNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    val searchPageNews = 1

    init{
        getNewsHeadlines("in")
    }

    fun getNewsHeadlines(countryCode: String) = viewModelScope.launch {
        newsHeadlines.postValue(Resource.Loading())
        val response = newsRepository.getNewsHeadlines(countryCode, headlinesPageNews)
        newsHeadlines.postValue(handleBreakingResponses(response))
    }

    fun getSearchedNews(searchText: String) = viewModelScope.launch {
        searchedNews.postValue(Resource.Loading())
        val response = newsRepository.searchNews(searchText, searchPageNews)
        searchedNews.postValue(handleSearchResponses(response))
    }

    private fun handleBreakingResponses(response: Response<NewsResponse>): Resource<NewsResponse>{
        if(response.isSuccessful){
            response.body()?.let {resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleSearchResponses(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun saveArticle(article: Article) = viewModelScope.launch {
        newsRepository.insertIntoDb(article)
    }

    fun getAllSavedNews() =
        newsRepository.getAllArticlesFromDb()

    fun deleteNewsArticle(article: Article) = viewModelScope.launch {
        newsRepository.deleteArticles(article)
    }



}