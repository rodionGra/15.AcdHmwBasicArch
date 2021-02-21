package com.a15acdhmwbasicarch.presentation.mainActivity

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.a15acdhmwbasicarch.App
import com.a15acdhmwbasicarch.R
import com.a15acdhmwbasicarch.databinding.ActivityMainBinding
import com.a15acdhmwbasicarch.presentation.showPostsFragment.ShowAllPostsFragment
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupInjection()
        updateLocalStorage()
        observeError()
        setupBinding()
        setupBasicFragment()

    }

    private fun setupInjection() {
        val app = this.application as App
        app.getComponent().inject(this)
    }

    private fun updateLocalStorage() {
        viewModel.updateRepo()
    }

    private fun observeError() {
        viewModel.errorLiveData.observe(this, {
            showError()
        })
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

    private fun showError() {
        Toast.makeText(this, R.string.error_text, Toast.LENGTH_LONG).show()
    }
}