package com.palindrome.palindromeapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.palindrome.palindromeapp.databinding.ActivitySecondScreenBinding
import com.palindrome.palindromeapp.model.User
import com.palindrome.palindromeapp.viewmodel.UserViewModel
import com.palindrome.palindromeapp.viewmodel.ViewModelFactory

class SecondScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondScreenBinding

    private val viewModel: UserViewModel by lazy {
        ViewModelProvider(this, ViewModelFactory())[UserViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupUI()
        setupObservers()
        setupListeners()

        viewModel.clearSelectedUser()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setupUI() {
        val name = intent.getStringExtra(EXTRA_NAME) ?: "User"
        binding.tvUserName.text = "Welcome, $name"
        binding.tvSelectedUserLabel.text = "" // Start empty
    }

    private fun setupObservers() {
        viewModel.selectedUser.observe(this) { user ->
            if (user != null) {
                binding.tvSelectedUserLabel.text = user.fullName
            }
        }
    }

    private fun setupListeners() {
        binding.btnChooseUser.setOnClickListener {
            val intent = Intent(this, ThirdScreenActivity::class.java)
            startActivityForResult(intent, REQUEST_USER)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_USER && resultCode == RESULT_OK) {
            data?.getParcelableExtra<User>("selected_user")?.let { selectedUser ->
                viewModel.selectUser(selectedUser)
            }
        }
    }

    companion object {
        const val EXTRA_NAME = "extra_name"
        const val REQUEST_USER = 100
    }
}
