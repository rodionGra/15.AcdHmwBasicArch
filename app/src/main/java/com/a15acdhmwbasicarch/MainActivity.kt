package com.a15acdhmwbasicarch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.a15acdhmwbasicarch.presentation.PostUiModel
import com.a15acdhmwbasicarch.PostComponent
import com.a15acdhmwbasicarch.databinding.ActivityMainBinding
import com.a15acdhmwbasicarch.presentation.BannedUserPostUiModel
import org.w3c.dom.Text

interface InfoView {
    fun showInfo(info: List<PostUiModel>)
    fun showError(error: UserPostError)
}

class MainActivity : AppCompatActivity(R.layout.activity_main), InfoView {

    private val binding : ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val adapter = PostRecycleViewAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val presentor = PostComponent.createPresenter(this)
        presentor.attachView(this)
        setupRecycleView()
        adapter.notifyDataSetChanged()
    }

    override fun showInfo(info: List<PostUiModel>) {
        Log.d("showInfoTag", "$info")
        findViewById<ProgressBar>(R.id.progress).visibility = View.GONE
        //findViewById<TextView>(R.id.tv_hello).text = info.toString()
        findViewById<TextView>(R.id.tv_hello).text = "showInfo"
        //adapter.submitList(info)
        //adapter.notifyDataSetChanged()
    }

    override fun showError(error: UserPostError) {
        findViewById<TextView>(R.id.tv_hello).text = error.toString()
    }

    private fun setupRecycleView(){
        binding.rvPosts.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvPosts.adapter = adapter
        adapter.submitList(listOf(BannedUserPostUiModel(100, "sdfsdfsdf")))
    }
}