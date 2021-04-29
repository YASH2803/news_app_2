package com.example.newsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.newsapp.R
import kotlinx.android.synthetic.main.activity_main.*

//269be87b60ba456f80caa103cbe7dd3e
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomAppBar.setupWithNavController(host_layout.findNavController())
        bottomNavigationView.background = null

//        main_activity_fab_search.setOnClickListener{
//            findNavController(R.id.host_layout).navigate(R.id.action_to_searchNewsFragment)
//        }
    }
}