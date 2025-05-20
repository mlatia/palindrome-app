package com.palindrome.palindromeapp.util

object PalindromeChecker {

    /**
     * Checks if a string is a palindrome.
     * A palindrome is a word, phrase, number, or other sequence of characters
     * that reads the same forward and backward, ignoring spaces, case, and punctuation.
     *
     * @param text The string to check
     * @return true if the string is a palindrome, false otherwise
     */
    fun isPalindrome(text: String): Boolean {
        val cleanText = text.replace("\\s+".toRegex(), "").lowercase()
        return cleanText == cleanText.reversed()
    }
}