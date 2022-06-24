package com.picpay.desafio.android.users

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.SavedStateHandle
import androidx.recyclerview.widget.LinearLayoutManager
import com.picpay.desafio.android.R
import com.picpay.desafio.android.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var adapter: UserListAdapter
    private val viewModel: UserViewModel by viewModel {
        parametersOf(
            SavedStateHandle()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setContentView(binding.root)

        setupUserAdapter()
        setupObservers()
    }

    private fun setupUserAdapter() {
        adapter = UserListAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun setupObservers() {
        viewModel.error.observe(this) { error ->
            if (error) showErrorMessage()
        }
    }

    private fun showErrorMessage() {
        val message = getString(R.string.error)
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT)
            .show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        viewModel.onUserSaveState()
        super.onSaveInstanceState(outState)
    }
}
