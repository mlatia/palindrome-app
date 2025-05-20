package com.palindrome.palindromeapp.viewmodel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.palindrome.palindromeapp.model.User
import com.palindrome.palindromeapp.repository.UserRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository) : ViewModel() {

    private val _users = MutableLiveData<List<User>>(emptyList())
    val users: LiveData<List<User>> = _users

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>(null)
    val error: LiveData<String?> = _error

    private val _selectedUser = MutableLiveData<User?>(null)
    val selectedUser: LiveData<User?> = _selectedUser

    private var currentPage = 1
    private var isLastPage = false
    private var totalPages = 1

    init {
        loadUsers(true)
    }

    fun loadUsers(isRefresh: Boolean = false) {
        if (isRefresh) {
            currentPage = 1
            isLastPage = false
        }

        if (isLastPage && !isRefresh) return

        viewModelScope.launch {
            repository.getUsers(currentPage, 6)
                .onStart { _isLoading.value = true }
                .onCompletion { _isLoading.value = false }
                .catch { e ->
                    _error.value = e.message
                }
                .collect { result ->
                    result.onSuccess { response ->
                        totalPages = response.total_pages
                        isLastPage = currentPage >= totalPages

                        val newUsers = if (isRefresh) {
                            response.data
                        } else {
                            val currentUsers = _users.value ?: emptyList()
                            currentUsers + response.data
                        }

                        _users.value = newUsers
                        currentPage++
                    }.onFailure { exception ->
                        _error.value = exception.message
                    }
                }
        }
    }

    fun selectUser(user: User) {
        _selectedUser.value = user
    }

    fun clearSelectedUser() {
        _selectedUser.value = null
    }
}
