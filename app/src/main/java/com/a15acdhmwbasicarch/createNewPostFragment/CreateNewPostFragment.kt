package com.a15acdhmwbasicarch.createNewPostFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.a15acdhmwbasicarch.R
import com.a15acdhmwbasicarch.databinding.CreateNewPostFragmentBinding

class CreateNewPostFragment : Fragment() {

    private val viewModel: CreateNewPostViewModel by viewModels()
    private lateinit var binding: CreateNewPostFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CreateNewPostFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
    }

    private fun setupListeners() {
        binding.btnAddNewPost.setOnClickListener {
            sendDataToBack()
        }
    }

    private fun sendDataToBack(){
        viewModel.sendDataToCache(binding.etTitle.text.toString(), binding.etBody.text.toString())
    }

    private fun closeCurrentFragment(){
        activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
    }

    companion object {
        fun newInstance() = CreateNewPostFragment()
    }

    //fun showError(enam ){}
}