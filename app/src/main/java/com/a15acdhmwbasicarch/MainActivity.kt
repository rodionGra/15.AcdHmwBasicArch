package com.a15acdhmwbasicarch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import com.a15acdhmwbasicarch.presentation.PostUiModel
import com.a15acdhmwbasicarch.PostComponent

interface InfoView {
    fun showInfo(info: List<PostUiModel>)
    fun showError(error: UserPostError)
}

class MainActivity : AppCompatActivity(), InfoView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val presentor = PostComponent.createPresenter(this)
        presentor.attachView(this)
    }

    override fun showInfo(info: List<PostUiModel>) {
        Log.d("tag1", "$info")
        findViewById<ProgressBar>(R.id.progress).visibility = View.GONE
    }

    override fun showError(error: UserPostError) {

    }
}