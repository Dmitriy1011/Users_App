package ru.testapp.cft_test.api

import retrofit2.Response
import retrofit2.http.GET
import ru.testapp.cft_test.dto.Results

interface ApiServiceUsers {
    @GET("?page=3&results=10&seed=abc")
    suspend fun getUsersLatest(): Response<Results>

    @GET("?page=3&results=10&seed=abc")
    suspend fun getAllUsers(): Response<Results>
}
