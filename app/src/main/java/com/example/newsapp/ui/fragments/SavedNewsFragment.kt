package com.example.newsapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.adapters.NewsAdapter
import com.example.newsapp.ui.MainActivity
import com.example.newsapp.viewmodels.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_saved_news.*

class SavedNewsFragment: Fragment(R.layout.fragment_saved_news) {
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        setupRecyclerView()
        newsAdapter.setOnItemClickListener {
            findNavController().navigate(SavedNewsFragmentDirections.actionSavedNewsFragmentToDetailNewsArticleFragment(it))
        }

        val itemTouchCallBack = object: ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = newsAdapter.currentList[position]
                viewModel.deleteNewsArticle(article)
                Snackbar.make(view, "News Article Deleted!", Snackbar.LENGTH_SHORT).
                        setAction("UNDO"){
                            viewModel.saveArticle(article)
                        }
                        .show()
            }
        }

        ItemTouchHelper(itemTouchCallBack).apply {
            attachToRecyclerView(rv_saved_news)
        }
        viewModel.getAllSavedNews().observe(viewLifecycleOwner, {articles ->
            newsAdapter.submitList(articles)
        })
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter(requireContext())
        rv_saved_news.apply{
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}