package com.example.airvet.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.airvet.repository.UsersRepository

class UsersViewModelFactory (private val repository: UsersRepository?): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(UsersViewModel::class.java)) {
            this.repository?.let { UsersViewModel(it) } as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}