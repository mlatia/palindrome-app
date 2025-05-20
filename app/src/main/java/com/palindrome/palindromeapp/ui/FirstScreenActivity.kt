package com.palindrome.palindromeapp.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.palindrome.palindromeapp.R
import com.palindrome.palindromeapp.databinding.ActivityFirstScreenBinding
import com.palindrome.palindromeapp.viewmodel.FirstScreenViewModel
import com.palindrome.palindromeapp.viewmodel.ViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import android.Manifest
import android.content.pm.PackageManager

class FirstScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFirstScreenBinding

    private val viewModel: FirstScreenViewModel by lazy {
        ViewModelProvider(this, ViewModelFactory())[FirstScreenViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    1001
                )
            }
        }

        setupObservers()
        setupListeners()
    }

    private fun setupObservers() {
        viewModel.isPalindrome.observe(this) { isPalindrome ->
            showPalindromeDialog(isPalindrome)
        }
    }

    private fun setupListeners() {
        binding.btnCheck.setOnClickListener {
            val text = binding.etPalindrome.text.toString().trim()
            if (text.isNotEmpty()) {
                viewModel.checkPalindrome(text)
            } else {
                Toast.makeText(this, "Please enter a text to check", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnNext.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            if (name.isNotEmpty()) {
                val intent = Intent(this, SecondScreenActivity::class.java).apply {
                    putExtra(SecondScreenActivity.EXTRA_NAME, name)
                }
                startActivity(intent)
            } else {
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showPalindromeDialog(isPalindrome: Boolean) {
        val message = if (isPalindrome) getString(R.string.is_palindrome) else getString(R.string.not_palindrome)

        MaterialAlertDialogBuilder(this)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}
