package com.example.newsapp.repository

import com.example.newsapp.api.RetrofitInstance
import com.example.newsapp.db.NewsDatabase
import com.example.newsapp.models.Article

class NewsRepository(
    val db : NewsDatabase
) {
    suspend fun getNewsHeadlines(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getHeadlines(countryCode, pageNumber)

    suspend fun searchNews(searchQuery : String, pageNumber: Int) =
        RetrofitInstance.api.searchNews(searchQuery, pageNumber)

    suspend fun insertIntoDb(article: Article) =
        db.getArticleDao().insert(article)

    fun getAllArticlesFromDb() =  db.getArticleDao().getAllArticle()

    suspend fun deleteArticles(article: Article) = db.getArticleDao().deleteArticles(article)
}