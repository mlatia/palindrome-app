package com.palindrome.palindromeapp.repository

import com.palindrome.palindromeapp.api.ApiService
import com.palindrome.palindromeapp.model.UserResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class UserRepository(private val apiService: ApiService) {

    fun getUsers(page: Int, perPage: Int): Flow<Result<UserResponse>> = flow {
        try {
            val response = apiService.getUsers(page, perPage)
            emit(Result.success(response))
        } catch (e: IOException) {
            emit(Result.failure(Exception("Network error. Please check your connection.")))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}