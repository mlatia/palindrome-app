package com.palindrome.palindromeapp.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.palindrome.palindromeapp.api.ApiService
import com.palindrome.palindromeapp.repository.UserRepository

class ViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(FirstScreenViewModel::class.java) -> {
                FirstScreenViewModel() as T
            }
            modelClass.isAssignableFrom(UserViewModel::class.java) -> {
                val apiService = ApiService.create()
                val repository = UserRepository(apiService)
                UserViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}