package com.a15acdhmwbasicarch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.a15acdhmwbasicarch.presentation.PostUiModel
import com.a15acdhmwbasicarch.PostComponent
import org.w3c.dom.Text

interface InfoView {
    fun showInfo(info: List<PostUiModel>)
    fun showError(error: UserPostError)
}

class MainActivity : AppCompatActivity(R.layout.activity_main), InfoView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val presentor = PostComponent.createPresenter(this)
        presentor.attachView(this)
    }

    override fun showInfo(info: List<PostUiModel>) {
        Log.d("showInfoTag", "$info")
        findViewById<ProgressBar>(R.id.progress).visibility = View.GONE
        findViewById<TextView>(R.id.tv_hello).text = info.toString()
    }

    override fun showError(error: UserPostError) {
        findViewById<TextView>(R.id.tv_hello).text = error.toString()
    }
}