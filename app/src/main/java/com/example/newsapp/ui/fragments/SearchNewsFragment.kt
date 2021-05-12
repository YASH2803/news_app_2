package com.example.newsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.adapters.NewsAdapter
import com.example.newsapp.ui.MainActivity
import com.example.newsapp.util.Resource
import com.example.newsapp.viewmodels.NewsViewModel
import kotlinx.android.synthetic.main.fragment_search_news.*

class SearchNewsFragment: Fragment(R.layout.fragment_search_news) {
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    private  val TAG = "SearchNewsFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        setupRecyclerView()

        newsAdapter.setOnItemClickListener {
            findNavController().navigate(SearchNewsFragmentDirections.actionSearchNewsFragmentToDetailNewsArticleFragment(it))
        }

        et_search.addTextChangedListener {editable->
            editable?.let{
                if(editable.toString().isNotEmpty()){
                    viewModel.getSearchedNews(editable.toString())
                }
            }
        }

        viewModel.searchedNews.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    pagination_progressBar.visibility = View.GONE
                    response.data?.let {
                        newsAdapter.submitList(it.articles)
                    }
                }
                is Resource.Error -> {
                    pagination_progressBar.visibility = View.GONE
                    response.message?.let {
                        Log.e(TAG, "An Error Occured $it", )
                    }
                }
                is Resource.Loading -> {
                    pagination_progressBar.visibility = View.VISIBLE
                }
            }

        })
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter(requireContext())
        rv_search_news.apply{
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}