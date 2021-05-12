package com.example.newsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.adapters.NewsAdapter
import com.example.newsapp.ui.MainActivity
import com.example.newsapp.util.Resource
import com.example.newsapp.viewmodels.NewsViewModel
import kotlinx.android.synthetic.main.fragment_headlines.*

class NewsHeadlinesFragment: Fragment(R.layout.fragment_headlines) {

    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter

    private val TAG = "NewsHeadlinesFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        setUpRecyclerView()

        newsAdapter.setOnItemClickListener {
            findNavController().navigate(NewsHeadlinesFragmentDirections.actionNewsHeadlinesFragmentToDetailNewsArticleFragment(it))
        }

        viewModel.newsHeadlines.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    pagination_progressbar.visibility = View.GONE
                    response.data?.let {
                        newsAdapter.submitList(it.articles)
                    }
                }
                is Resource.Error -> {
                    pagination_progressbar.visibility = View.GONE
                    response.message?.let {
                        Log.e(TAG, "An Error Occured $it", )
                    }
                }
                is Resource.Loading -> {
                    pagination_progressbar.visibility = View.VISIBLE
                }
            }

        })
    }

    private fun setUpRecyclerView() {
        newsAdapter = NewsAdapter(requireContext())
        rv_news_headlines.apply{
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}