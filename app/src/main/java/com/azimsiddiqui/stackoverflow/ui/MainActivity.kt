package com.azimsiddiqui.stackoverflow.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.azimsiddiqui.stackoverflow.data.Question
import com.azimsiddiqui.stackoverflow.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), QuestionItemClickListener {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: QuestionsViewModel by viewModels()
    private lateinit var questionListAdapter: QuestionListAdapter
    private var questionList = ArrayList<Question>()
    private lateinit var viewCount: String
    private lateinit var answerCount: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        viewModel.getQuestionList()
        observerLiveEvent()
        initRecyclerView()
        setupSearch()
        setUpFilter()

    }

    private fun setupSearch() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s.toString().let { query ->
                    if (query.isNotEmpty()) {
                        var filteredList = questionList.filter {
                            it.title.contains(query, true) ||
                                    it.owner.display_name.contains(query, true)
                        }
                        if (filteredList.isEmpty()) {
                            binding.tvNoResult.visibility = View.VISIBLE
                            showViewAndAnswerCount(false)
                        } else {
                            showViewAndAnswerCount(true)
                            binding.tvNoResult.visibility = View.GONE
                        }
                        viewCount = filteredList.map { question -> question.view_count }.sum().toString()
                        answerCount = filteredList.map { question -> question.answer_count }.sum().toString()
                        populateViewAndAnswerCount(viewCount,answerCount)
                        questionListAdapter.setData(filteredList)
                    } else {
                        questionListAdapter.setData(questionList)
                    }

                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun setUpFilter() {
        binding.ivFilter.setOnClickListener {
           Toast.makeText(this,"Filter is not implemented yet",Toast.LENGTH_SHORT).show()
            //ItemListDialogFragment.newInstance(30).show(supportFragmentManager, "dialog")
        }
    }

    private fun initRecyclerView() {

        binding.rvQuestionList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
            hasFixedSize()
        }
    }

    private fun observerLiveEvent() {
        showProgressBar(true)
        showViewAndAnswerCount(false)

        viewModel.questionsListLiveData.observe(this, Observer {
            questionList.addAll(it)
            viewCount = questionList.map { question -> question.view_count }.sum().toString()
            answerCount = questionList.map { question -> question.answer_count }.sum().toString()

            showViewAndAnswerCount(true)
            populateViewAndAnswerCount(viewCount, answerCount)

            showProgressBar(false)
            questionListAdapter = QuestionListAdapter(this)
            binding.rvQuestionList.adapter = questionListAdapter
            questionListAdapter.setData(it)
        })
    }

    override fun onClick(url: String) {
        openUrl(url)
    }

    private fun openUrl(link: String) =
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(link)))

    private fun showProgressBar(boolean: Boolean) {
        binding.pbLoading.visibility = if (boolean) View.VISIBLE else View.GONE
    }

    private fun showViewAndAnswerCount(boolean: Boolean) {
        if (boolean) {
            binding.averageViewCountCard.visibility = View.VISIBLE
            binding.averageAnswerCountCard.visibility = View.VISIBLE
        } else {
            binding.averageViewCountCard.visibility = View.GONE
            binding.averageAnswerCountCard.visibility = View.GONE
        }
    }

    private fun populateViewAndAnswerCount(viewCount: String, answerCount: String) {
        with(binding) {
            txtAverageViewCount.text = viewCount
            txtAverageAnswerCount.text = answerCount
        }

    }
}