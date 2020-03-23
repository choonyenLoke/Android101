package com.example.opentriva

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.opentriva.apiservice.ApiServiceInterface
import com.example.opentriva.apiservice.RetrofitService
import com.example.opentriva.model.Result
import com.example.opentriva.viewmodel.QuestionViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.question_layout.*
import java.util.*

class QuestionActivity : AppCompatActivity(), View.OnClickListener {

    private var category: Int? = null
    private var difficulty: String? = null
    private var type: String? = null

    private var token: String? = null

    private var categoryId: Int? = null
    private var paraDifficult: String? = null
    private var paraType: String? = null

    var correct: Spanned? = null

    private lateinit var questionViewModel: QuestionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.question_layout)
        btnDifficulty.visibility = View.INVISIBLE
        btn_first.visibility = View.INVISIBLE
        btn_second.visibility = View.INVISIBLE
        btn_third.visibility = View.INVISIBLE
        btn_forth.visibility = View.INVISIBLE
        question_text.visibility = View.INVISIBLE

        intent.let {
            category = it.getIntExtra("Category", 0)
            difficulty = it.getStringExtra("Difficulty")?.toLowerCase(Locale.ROOT)
            type = it.getStringExtra("Type")?.toLowerCase(Locale.ROOT)
        }

        questionViewModel = ViewModelProvider(this).get(QuestionViewModel::class.java)
        questionViewModel.getNewToken()
        questionViewModel.token.observe(this, androidx.lifecycle.Observer {
            if(it.responseCode == 0){
                token = it.token
                getQuestion(token.toString(), category, difficulty, type)
            }
        })

        btnRoll.setOnClickListener {
            btn_first.setBackgroundColor(Color.WHITE)
            btn_second.setBackgroundColor(Color.WHITE)
            btn_third.setBackgroundColor(Color.WHITE)
            btn_forth.setBackgroundColor(Color.WHITE)
            getQuestion(token.toString(), category, difficulty, type)
            load_question.visibility = View.VISIBLE
            question_text.visibility = View.INVISIBLE
            btn_first.visibility = View.INVISIBLE
            btn_second.visibility = View.INVISIBLE
            btn_third.visibility = View.INVISIBLE
            btn_forth.visibility = View.INVISIBLE
            btnDifficulty.visibility = View.INVISIBLE
        }
    }

    private fun getQuestion(token: String, category: Int?, difficulty: String?, type: String?) {
        if (category != 0) {
            categoryId = category
        }
        else {
            categoryId = null
        }
        if (difficulty == "default") {
            paraDifficult = null
        }
        else {
            paraDifficult = difficulty
        }
        if (type == "default") {
            paraType = null
        }
        else {
            paraType = type
        }
        questionViewModel.getQuestionParams(token, categoryId, paraDifficult, paraType)
        questionViewModel.questionResult.observe(this, androidx.lifecycle.Observer {
            onSuccess(it)
        })
    }

    private fun onSuccess(data: Result) {
        if(data.responseCode.compareTo(4) == 0) {
            questionViewModel.resetToken(token.toString())
            questionViewModel.resetToken.observe(this, androidx.lifecycle.Observer {
                if(it.responseCode == 0){
                    token = it.token
                    getQuestion(it.token, category,difficulty,type)
                }
            })
        }
        else if(data.responseCode.compareTo(3) == 0) {
            questionViewModel.getNewToken()
            questionViewModel.token.observe(this, androidx.lifecycle.Observer {
                if(it.responseCode == 0){
                    token = it.token
                    getQuestion(it.token, category, difficulty, type)
                }
            })
        }
        else if(data.responseCode.compareTo(0) == 0)
        {
            load_question.visibility = View.GONE
            question_text.text = Html.fromHtml(data.results[0].question, Html.FROM_HTML_MODE_COMPACT)
            question_text.visibility = View.VISIBLE
            btnDifficulty.text = Html.fromHtml(data.results[0].difficulty, Html.FROM_HTML_MODE_COMPACT)

            if(btnDifficulty.text == Html.fromHtml("easy", Html.FROM_HTML_MODE_COMPACT) ){
                btnDifficulty.setBackgroundColor(Color.GREEN)
            }
            else if(btnDifficulty.text == Html.fromHtml("medium", Html.FROM_HTML_MODE_COMPACT)){
                btnDifficulty.setBackgroundColor(Color.YELLOW)
            }
            else if(btnDifficulty.text == Html.fromHtml("hard", Html.FROM_HTML_MODE_COMPACT)){
                btnDifficulty.setBackgroundColor(Color.RED)
            }

            btnDifficulty.visibility = View.VISIBLE
            if(data.results[0].type == "multiple")
            {
                btn_first.visibility = View.VISIBLE
                btn_second.visibility = View.VISIBLE
                btn_third.visibility = View.VISIBLE
                btn_forth.visibility = View.VISIBLE

                var answerList = mutableListOf<String>()
                correct = Html.fromHtml(data.results[0].correctAnswer, Html.FROM_HTML_MODE_COMPACT)
                answerList.add(data.results[0].correctAnswer)
                answerList.add(data.results[0].incorrectAnswers[0])
                answerList.add(data.results[0].incorrectAnswers[1])
                answerList.add(data.results[0].incorrectAnswers[2])

                btn_first.text = Html.fromHtml(answerList.random(), Html.FROM_HTML_MODE_COMPACT)
                answerList.remove(btn_first.text.toString())
                btn_second.text = Html.fromHtml(answerList.random(), Html.FROM_HTML_MODE_COMPACT)
                answerList.remove(btn_second.text.toString())
                btn_third.text = Html.fromHtml(answerList.random(), Html.FROM_HTML_MODE_COMPACT)
                answerList.remove(btn_third.text.toString())
                btn_forth.text = Html.fromHtml(answerList.random(), Html.FROM_HTML_MODE_COMPACT)

                btn_first.setOnClickListener(this)
                btn_second.setOnClickListener(this)
                btn_third.setOnClickListener(this)
                btn_forth.setOnClickListener(this)
            }
            else
            {
                btn_first.visibility = View.VISIBLE
                btn_second.visibility = View.VISIBLE
                btn_third.visibility = View.INVISIBLE
                btn_forth.visibility = View.INVISIBLE
                correct = Html.fromHtml(data.results[0].correctAnswer, Html.FROM_HTML_MODE_COMPACT)
                var booleanList = mutableListOf<String>()
                booleanList.add(data.results[0].correctAnswer)
                booleanList.add(data.results[0].incorrectAnswers[0])

                btn_first.text = Html.fromHtml(booleanList.random(), Html.FROM_HTML_MODE_COMPACT)
                booleanList.remove(btn_first.text.toString())
                btn_second.text = Html.fromHtml(booleanList.random(), Html.FROM_HTML_MODE_COMPACT)
                btn_first.setOnClickListener(this)
                btn_second.setOnClickListener(this)
            }
        }
    }

    override fun onClick(v: View?) {
        if(v?.id == R.id.btn_first){
            if(btn_first.text == correct){
                btn_first.setBackgroundColor(Color.GREEN)
                btn_second.setBackgroundColor(Color.WHITE)
                btn_third.setBackgroundColor(Color.WHITE)
                btn_forth.setBackgroundColor(Color.WHITE)
            }
            else{
                btn_first.setBackgroundColor(Color.RED)
                btn_second.setBackgroundColor(Color.WHITE)
                btn_third.setBackgroundColor(Color.WHITE)
                btn_forth.setBackgroundColor(Color.WHITE)
            }
        }
        else if(v?.id == R.id.btn_second){
            if(btn_second.text == correct){
                btn_second.setBackgroundColor(Color.GREEN)
                btn_first.setBackgroundColor(Color.WHITE)
                btn_third.setBackgroundColor(Color.WHITE)
                btn_forth.setBackgroundColor(Color.WHITE)
            }
            else{
                btn_second.setBackgroundColor(Color.RED)
                btn_first.setBackgroundColor(Color.WHITE)
                btn_third.setBackgroundColor(Color.WHITE)
                btn_forth.setBackgroundColor(Color.WHITE)
            }
        }
        else if(v?.id == R.id.btn_third){
            if(btn_third.text == correct){
                btn_third.setBackgroundColor(Color.GREEN)
                btn_first.setBackgroundColor(Color.WHITE)
                btn_second.setBackgroundColor(Color.WHITE)
                btn_forth.setBackgroundColor(Color.WHITE)
            }
            else
            {
                btn_third.setBackgroundColor(Color.RED)
                btn_first.setBackgroundColor(Color.WHITE)
                btn_second.setBackgroundColor(Color.WHITE)
                btn_forth.setBackgroundColor(Color.WHITE)
            }
        }
        else if(v?.id == R.id.btn_forth){
            if(btn_forth.text == correct){
                btn_forth.setBackgroundColor(Color.GREEN)
                btn_first.setBackgroundColor(Color.WHITE)
                btn_second.setBackgroundColor(Color.WHITE)
                btn_third.setBackgroundColor(Color.WHITE)
            }
            else {
                btn_forth.setBackgroundColor(Color.RED)
                btn_first.setBackgroundColor(Color.WHITE)
                btn_second.setBackgroundColor(Color.WHITE)
                btn_third.setBackgroundColor(Color.WHITE)
            }
        }
    }


}
