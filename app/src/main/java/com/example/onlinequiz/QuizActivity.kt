package com.example.onlinequiz

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.onlinequiz.databinding.ActivityQuizBinding
import com.example.onlinequiz.databinding.ScoredialogueBinding
import kotlinx.coroutines.selects.select

class QuizActivity : AppCompatActivity(), View.OnClickListener {

    companion object{
        var questionModelList : List<QuestionModel> = listOf()
        var time : String = ""
    }

    lateinit var binding: ActivityQuizBinding
    var currentQuestionIndex = 0
    var selectedAnswer = ""
    var score = 0;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        loadQuestions()
        enableEdgeToEdge()
        startTimer()
        setContentView(binding.root)
        binding.apply {
            button0.setOnClickListener(this@QuizActivity)
            button1.setOnClickListener(this@QuizActivity)
            button2.setOnClickListener(this@QuizActivity)
            button3.setOnClickListener(this@QuizActivity)
            nextButton.setOnClickListener(this@QuizActivity)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun startTimer() {
        val totalTimeinMillis = time.toInt() * 60 * 1000L
        object :  CountDownTimer(totalTimeinMillis, 1000L){
            override fun onTick(millisUntilFinished: Long) {
                val seconds = millisUntilFinished /1000
                val minutes = seconds/60
                val remainingSeconds = seconds % 60
                binding.timerTextview.text = String.format("%02d:%02d", minutes, remainingSeconds)

            }

            override fun onFinish() {
                TODO("Not yet implemented")
                //finish the quiz
            }

        }.start()

    }

    @SuppressLint("SetTextI18n")
    private fun loadQuestions(){
        selectedAnswer = ""
        if(currentQuestionIndex == questionModelList.size) {
            finishQuiz()
            return
        }
        binding.apply {
            questionLayout.text = "Question ${currentQuestionIndex + 1}/${questionModelList.size}"
            progressBar.progress = (currentQuestionIndex.toFloat() / questionModelList.size.toFloat() * 100).toInt()
            questionTextview.text = questionModelList[currentQuestionIndex].question
            button0.text = questionModelList[currentQuestionIndex].options[0]
            button1.text = questionModelList[currentQuestionIndex].options[1]
            button2.text = questionModelList[currentQuestionIndex].options[2]
            button3.text = questionModelList[currentQuestionIndex].options[3]
        }
    }

    override fun onClick(view: View?) {
        binding.apply {
            button0.setBackgroundColor(getColor(R.color.grey))
            button1.setBackgroundColor(getColor(R.color.grey))
            button2.setBackgroundColor(getColor(R.color.grey))
            button3.setBackgroundColor(getColor(R.color.grey))
        }
        val clickedBtn = view as Button
        if(clickedBtn.id==R.id.nextButton){
            if (selectedAnswer == questionModelList[currentQuestionIndex].correct){
                score++
                Log.i("Score of Quiz", score.toString())
            }
            currentQuestionIndex++
            loadQuestions()
        }else
        {
            //answer is clicked
            selectedAnswer = clickedBtn.text.toString()
            clickedBtn.setBackgroundColor(getColor(R.color.orange))
        }
    }

    private fun finishQuiz(){
        val totalQuestions = questionModelList.size
        val percentage = ((score.toFloat() / totalQuestions.toFloat()) * 100).toInt()

        val dialogueBinding = ScoredialogueBinding.inflate(layoutInflater)
        dialogueBinding.apply {
            scoreProgressBar.progress = percentage
            scoreProgressText.text = "$percentage %"
            if(percentage>60){
                scoreTitle.text = "Congrats you have passed!"
                scoreTitle.setTextColor(Color.BLUE)
            }else{
                scoreTitle.text = "Oops you have failed"
                scoreTitle.setTextColor(Color.RED)
            }
            scoreSubtitle.text ="$score out of $totalQuestions are correct"
            finishButton.setOnClickListener {
                finish()
            }
        }
        AlertDialog.Builder(this)
            .setView(dialogueBinding.root)
            .setCancelable(false)
            .show()
    }

}