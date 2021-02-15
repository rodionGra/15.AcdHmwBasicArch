package com.a15acdhmwbasicarch.showPostsFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.a15acdhmwbasicarch.R
import com.a15acdhmwbasicarch.createNewPostFragment.CreateNewPostFragment
import com.a15acdhmwbasicarch.databinding.ShowAllPostsFragmentBinding
import com.a15acdhmwbasicarch.presentation.PostRecycleViewAdapter
import com.a15acdhmwbasicarch.presentation.PostUiModel

class ShowAllPostsFragment : Fragment() {

    private val viewModel: ShowAllPostsViewModel by viewModels()
    private lateinit var binding: ShowAllPostsFragmentBinding
    private val postRecycleViewAdapter = PostRecycleViewAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ShowAllPostsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("onViewCreated", "onViewCreated")
        observeGitHubRepos()
        setupListeners()
        setupRecyclerView()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getPosts()
    }

    private fun observeGitHubRepos() {
        viewModel.reposLiveData.observe(viewLifecycleOwner, {
            updatePostsRecyclerView(it)
            binding.progress.visibility = View.GONE
        })
    }

    private fun setupRecyclerView() {
        binding.rvPosts.apply {
            layoutManager =
                LinearLayoutManager(this@ShowAllPostsFragment.context, LinearLayoutManager.VERTICAL, false)
            adapter = postRecycleViewAdapter
            addItemDecoration(
                DividerItemDecoration(
                    this@ShowAllPostsFragment.context,
                    RecyclerView.VERTICAL
                )
            )
        }
    }

    private fun updatePostsRecyclerView(items: List<PostUiModel>) {
        postRecycleViewAdapter.submitList(items)
    }

    private fun setupListeners() {
        binding.btnCreateNewPost.setOnClickListener {
            startAddNewPostFragment()
        }
    }

    private fun startAddNewPostFragment() {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.main_content_fragment, CreateNewPostFragment.newInstance())
            ?.addToBackStack(null)
            ?.commit()
    }

    companion object {
        fun newInstance() = ShowAllPostsFragment()
    }

}