package com.example.newsapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.newsapp.models.Article

@Dao
interface ArticlesDao {

    @Insert
    suspend fun insert(article: Article): Long

    @Query("SELECT * FROM articles")
    fun getAllArticle (): LiveData<List<Article>>

    @Delete
    suspend fun deleteArticles(article: Article)

}