package com.azimsiddiqui.stackoverflow.network

import com.azimsiddiqui.stackoverflow.data.QuestionResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("questions?key=ZiXCZbWaOwnDgpVT9Hx8IA((&order=desc&sort=activity&site=stackoverflow")
    suspend fun getQuestions():Response<QuestionResponse>


}