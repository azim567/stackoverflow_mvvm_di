package com.azimsiddiqui.stackoverflow.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azimsiddiqui.stackoverflow.data.Question
import com.azimsiddiqui.stackoverflow.data.QuestionResponse
import com.azimsiddiqui.stackoverflow.network.QuestionsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionsViewModel @Inject constructor(private val questionsRepository : QuestionsRepository) : ViewModel(){

    private var _questionsListLiveData = MutableLiveData<List<Question>>()
    val questionsListLiveData: LiveData<List<Question>>
        get() = _questionsListLiveData


    fun getQuestionList(){
        viewModelScope.launch {
            val result=questionsRepository.getQuestions()
            if(result.isSuccessful){
                val response=result.body() as QuestionResponse
                _questionsListLiveData.value= response.items
            }
            else{

                Log.i("error", "getQuestionList: ${result.message()}")
            }

        }
    }

}