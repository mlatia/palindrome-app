package com.palindrome.palindromeapp.viewmodel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.palindrome.palindromeapp.util.PalindromeChecker

class FirstScreenViewModel : ViewModel() {

    private val _isPalindrome = MutableLiveData<Boolean>()
    val isPalindrome: LiveData<Boolean> = _isPalindrome

    fun checkPalindrome(text: String) {
        _isPalindrome.value = PalindromeChecker.isPalindrome(text)
    }
}