package com.a15acdhmwbasicarch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.a15acdhmwbasicarch.presentation.PostUiModel
import com.a15acdhmwbasicarch.databinding.ActivityMainBinding
import com.a15acdhmwbasicarch.presentation.PostRecycleViewAdapter
import com.a15acdhmwbasicarch.showPostsFragment.ShowAllPostsFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        setupBasicFragment()
    }

    private fun setupBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setupBasicFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.main_content_fragment, ShowAllPostsFragment.newInstance())
            .commit()
    }
}