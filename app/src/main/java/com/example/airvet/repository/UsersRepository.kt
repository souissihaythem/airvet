package com.example.airvet.repository

import com.example.airvet.retrofit.RetrofitService

class UsersRepository(private val retrofitService: RetrofitService) {
    suspend fun getAllUsers( results:Int) = retrofitService.getAllUsers(results)
}