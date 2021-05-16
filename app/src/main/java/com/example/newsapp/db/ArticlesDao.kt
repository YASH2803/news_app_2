package com.example.newsapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.newsapp.models.Article

@Dao
interface ArticlesDao {

    @Insert
    suspend fun insert(article: Article): Long

    @Query("SELECT * FROM articles WHERE isFav=1")
    fun getAllArticle (): LiveData<List<Article>>

    @Delete
    suspend fun deleteArticles(article: Article)

    @Query("DELETE FROM articles WHERE title=:title")
    suspend fun deleteWithTitle(title:String)
}