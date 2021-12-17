package com.example.airvet.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.airvet.model.UserResult
import com.example.airvet.repository.UsersRepository
import kotlinx.coroutines.*

class UsersViewModel constructor(private val mainRepository: UsersRepository) : ViewModel() {
    val errorMessage = MutableLiveData<String>()
    val movieList = MutableLiveData<UserResult>()
    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    val loading = MutableLiveData<Boolean>()

    fun getAllUsers(results:Int) {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = mainRepository.getAllUsers(results)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    movieList.postValue(response.body())
                    loading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }
    }
    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }
    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}