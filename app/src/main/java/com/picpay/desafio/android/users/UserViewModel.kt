package com.picpay.desafio.android.users

import androidx.lifecycle.*
import com.picpay.desafio.android.domain.models.UserModel
import com.picpay.desafio.android.domain.usecases.UserUseCase
import com.picpay.desafio.android.domain.utils.Success
import kotlinx.coroutines.launch

class UserViewModel(
    private val userUseCase: UserUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var _users: MutableLiveData<List<UserModel>> = MutableLiveData(emptyList())
    private var _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    private var _error: MutableLiveData<Boolean> = MutableLiveData(false)

    var users: LiveData<List<UserModel>> = _users
    var isLoading: LiveData<Boolean> = _isLoading
    var error: LiveData<Boolean> = _error

    init {
        viewModelScope.launch {
            val savedUsers = savedStateHandle.getLiveData<List<UserModel>>(SAVED_USERS)
            if (savedUsers.value.isNullOrEmpty()) {
                fetchUsers()
            } else {
                _users.value = savedUsers.value
            }
        }
    }

    private suspend fun fetchUsers() {
        _isLoading.value = true
        when (val response = userUseCase.getUsers()) {
            is Success -> _users.value = response.data
            else -> {
                _error.value = true
            }
        }
        _isLoading.value = false
    }

    fun onUserSaveState() {
        savedStateHandle[SAVED_USERS] = _users.value
    }

    companion object {
        private const val SAVED_USERS = "SAVED_USERS"
    }
}