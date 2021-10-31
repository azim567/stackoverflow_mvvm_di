package com.azimsiddiqui.stackoverflow.network


import javax.inject.Inject

class QuestionsRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getQuestions() = apiService.getQuestions()

}