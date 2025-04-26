package com.example.onlinequiz

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.onlinequiz.databinding.ActivityQuizBinding

class QuizActivity : AppCompatActivity() {

    companion object{
        var questionModelList : List<QuestionModel> = listOf()
    }

    lateinit var binding: ActivityQuizBinding
    var currentQuestionIndex = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        loadQuestions()
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    @SuppressLint("SetTextI18n")
    private fun loadQuestions(){
        binding.apply {
            questionLayout.text = "Question ${currentQuestionIndex + 1}/${questionModelList.size}"
            progressBar.progress = (currentQuestionIndex.toFloat() / questionModelList.size.toFloat()).toInt()
            questionTextview.text = questionModelList[currentQuestionIndex].question
            button0.text = questionModelList[currentQuestionIndex].options[0]
            button1.text = questionModelList[currentQuestionIndex].options[1]
            button2.text = questionModelList[currentQuestionIndex].options[2]
            button3.text = questionModelList[currentQuestionIndex].options[3]
        }
    }

}