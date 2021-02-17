package com.a15acdhmwbasicarch.createNewPostFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.a15acdhmwbasicarch.App
import com.a15acdhmwbasicarch.databinding.CreateNewPostFragmentBinding
import javax.inject.Inject

class CreateNewPostFragment : Fragment() {

    @Inject
    lateinit var viewModel: CreateNewPostViewModel
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

        val app = requireActivity().application as App
        app.getComponent().inject(this)

        setupListeners()
        observeErrorInput()
    }

    private fun setupListeners() {
        binding.btnAddNewPost.setOnClickListener {
            sendDataToBack()
        }
    }

    private fun observeErrorInput(){
        viewModel.stringErrorLiveData.observe(viewLifecycleOwner){
            if (it.isEmpty()){
                closeCurrentFragment()
            }
            else{
                binding.tvInputErrors.text = it
            }
        }
    }

    private fun sendDataToBack() {
        viewModel.sendDataToCache(binding.etTitle.text.toString(), binding.etBody.text.toString())
    }

    private fun closeCurrentFragment() {
        requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
    }

    companion object {
        fun newInstance() = CreateNewPostFragment()
    }
}