package com.a15acdhmwbasicarch.presentation.showPostsFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.a15acdhmwbasicarch.App
import com.a15acdhmwbasicarch.R
import com.a15acdhmwbasicarch.presentation.createNewPostFragment.CreateNewPostFragment
import com.a15acdhmwbasicarch.databinding.ShowAllPostsFragmentBinding
import com.a15acdhmwbasicarch.presentation.PostRecycleViewAdapter
import com.a15acdhmwbasicarch.presentation.PostUiModel
import javax.inject.Inject


class ShowAllPostsFragment : Fragment() {

    @Inject
    lateinit var viewModel: ShowAllPostsViewModel

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

        setupDi()
        observeLiveData()
        setupListeners()
        setupRecyclerView()
        setupData()
    }

    private fun setupDi() {
        val app = requireActivity().application as App
        app.getComponent().inject(this)
    }

    private fun observeLiveData() {
        viewModel.postsLiveData.observe(viewLifecycleOwner, {
            updatePostsRecyclerView(it)
        })
    }

    private fun setupRecyclerView() {
        binding.rvPosts.apply {
            layoutManager = LinearLayoutManager(
                this@ShowAllPostsFragment.context,
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = postRecycleViewAdapter
            this.addItemDecoration(
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

    private fun setupData() {
        viewModel.getPosts()
    }

    private fun startAddNewPostFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .add(R.id.main_content_fragment, CreateNewPostFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }

    companion object {
        fun newInstance() = ShowAllPostsFragment()
    }

}