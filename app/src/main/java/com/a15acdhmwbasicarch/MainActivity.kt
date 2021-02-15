package com.a15acdhmwbasicarch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.a15acdhmwbasicarch.presentation.PostUiModel
import com.a15acdhmwbasicarch.databinding.ActivityMainBinding
import com.a15acdhmwbasicarch.presentation.InfoPresenter
import com.a15acdhmwbasicarch.presentation.PostRecycleViewAdapter
import com.a15acdhmwbasicarch.showPostsFragment.ShowAllPostsFragment

interface InfoView {
    fun showUsersPost(info: List<PostUiModel>)
    fun showError(error: Int)
}

class MainActivity : AppCompatActivity()/*, InfoView*/ {

    private lateinit var binding: ActivityMainBinding
    private val postRecycleViewAdapter = PostRecycleViewAdapter()
    private lateinit var presenter: InfoPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        setupBasicFragment()
        /*setupRecycleView()*/
        /*setupPresenter()*/
    }
/*
    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }*/

    /*override fun showUsersPost(info: List<PostUiModel>) {
        Log.d("showInfoTag", "$info")
        binding.progress.visibility = View.GONE
        postRecycleViewAdapter.submitList(info)
    }

    override fun showError(error: Int) {
        binding.apply {
            progress.visibility = View.GONE
            tvError.visibility = View.VISIBLE
            tvError.text = resources.getString(error)
        }
    }*/

    private fun setupBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setupBasicFragment(){
        supportFragmentManager.beginTransaction()
            .add(R.id.main_content_fragment, ShowAllPostsFragment.newInstance() )
            .commitAllowingStateLoss()
    }

    /*private fun setupRecycleView() {
        binding.rvPosts.apply {
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            adapter = postRecycleViewAdapter
            addItemDecoration(DividerItemDecoration(this@MainActivity, RecyclerView.VERTICAL))
        }
    }*/

    /*private fun setupPresenter() {
        presenter = PostComponent.createPresenter(this)
        presenter.attachView(this)
    }*/
}