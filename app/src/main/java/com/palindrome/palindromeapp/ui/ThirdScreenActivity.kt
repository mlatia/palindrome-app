package com.palindrome.palindromeapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.palindrome.palindromeapp.adapter.UserAdapter
import com.palindrome.palindromeapp.databinding.ActivityThirdScreenBinding
import com.palindrome.palindromeapp.model.User
import com.palindrome.palindromeapp.viewmodel.UserViewModel
import com.palindrome.palindromeapp.viewmodel.ViewModelFactory

class ThirdScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityThirdScreenBinding

    // Replace viewModels delegate with lazy initialization
    private val viewModel: UserViewModel by lazy {
        ViewModelProvider(this, ViewModelFactory())[UserViewModel::class.java]
    }

    private lateinit var userAdapter: UserAdapter

    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupRecyclerView()
        setupObservers()
        setupListeners()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setupRecyclerView() {
        userAdapter = UserAdapter { user ->
            onUserSelected(user)
        }

        binding.rvUsers.apply {
            layoutManager = LinearLayoutManager(this@ThirdScreenActivity)
            adapter = userAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                    if (!isLoading && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                    ) {
                        viewModel.loadUsers(false)
                    }
                }
            })
        }
    }

    private fun setupObservers() {
        viewModel.users.observe(this) { users ->
            userAdapter.submitList(users)
            updateEmptyState(users.isEmpty())
        }

        viewModel.isLoading.observe(this) { loading ->
            isLoading = loading
            binding.progressBar.visibility = if (loading && userAdapter.itemCount == 0) View.VISIBLE else View.GONE
            binding.swipeRefreshLayout.isRefreshing = loading && userAdapter.itemCount > 0
        }

        viewModel.error.observe(this) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupListeners() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.loadUsers(true)
        }
    }

    private fun updateEmptyState(isEmpty: Boolean) {
        binding.tvEmptyState.visibility = if (isEmpty && !isLoading) View.VISIBLE else View.GONE
    }

    private fun onUserSelected(user: User) {
        val resultIntent = Intent().apply {
            putExtra("selected_user", user)
        }
        setResult(RESULT_OK, resultIntent)
        finish()
    }
}