package com.example.newsapp.ui.fragments

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.newsapp.R
import com.example.newsapp.ui.MainActivity
import com.example.newsapp.viewmodels.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_detail_news_article.*

class DetailNewsArticleFragment: Fragment(R.layout.fragment_detail_news_article) {
    private lateinit var viewModel: NewsViewModel
    val args: DetailNewsArticleFragmentArgs by navArgs()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        val article = args.article
        detailed_news_webView.apply {
            webViewClient = WebViewClient()
            loadUrl(article.url)
        }

        detailed_news_fab.setOnClickListener {
            viewModel.saveArticle(article)
            Snackbar.make(view, "Article saved successfully", Snackbar.LENGTH_SHORT).show()
        }


    }
}