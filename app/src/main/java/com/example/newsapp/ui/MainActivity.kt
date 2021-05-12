package com.example.newsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.newsapp.R
import com.example.newsapp.db.NewsDatabase
import com.example.newsapp.repository.NewsRepository
import com.example.newsapp.viewmodels.NewsViewModel
import com.example.newsapp.viewmodels.NewsViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_main.*

//269be87b60ba456f80caa103cbe7dd3e
class MainActivity : AppCompatActivity() {

    lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val newsRepository = NewsRepository(NewsDatabase(this))
        val viewModelProvider = NewsViewModelProviderFactory(newsRepository)
        viewModel = ViewModelProvider(this, viewModelProvider).get(NewsViewModel::class.java)


        bottomNavigationView.setupWithNavController(host_layout.findNavController())


    }
}