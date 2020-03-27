package com.example.opentriva.view

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.view.View
import android.view.animation.AnimationUtils
import com.example.opentriva.R
import com.example.opentriva.model.Result
import com.example.opentriva.viewmodel.QuestionViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.configure_question.*
import kotlinx.android.synthetic.main.question_layout.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class QuestionActivity : AppCompatActivity(), View.OnClickListener {

    private var category: Int? = null
    private var difficulty: String? = null
    private var type: String? = null
    private var tokenInit: String? = null

    var correct: Spanned? = null

    private val questionViewModel: QuestionViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.question_layout)

        val actionbar = supportActionBar
        actionbar?.title = "Open Trivia DB"
        actionbar?.setDisplayHomeAsUpEnabled(true)

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

        questionViewModel.getNewToken()
        questionViewModel.tokenNew.observe(this, androidx.lifecycle.Observer {
            tokenInit = it.token
            questionViewModel.getQuestion(tokenInit.toString(), category, difficulty, type)
            questionViewModel.questionResult.observe(this, androidx.lifecycle.Observer {
                onSuccess(it)
            })
            questionViewModel.status.observe(this, androidx.lifecycle.Observer {
                if(it == false){
                    load_question.visibility = View.GONE

                    val rootView = findViewById<View>(R.id.rootView)
                    val snack = Snackbar.make(rootView,"No Result Found, Please Refine Criteria.", Snackbar.LENGTH_INDEFINITE)
                    snack.setAction("BACK") {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                    snack.show()
                }
            })
        })

        btnRoll.setOnClickListener {
            btnRoll.setBackgroundColor(Color.LTGRAY)
            btn_first.setBackgroundColor(Color.WHITE)
            btn_second.setBackgroundColor(Color.WHITE)
            btn_third.setBackgroundColor(Color.WHITE)
            btn_forth.setBackgroundColor(Color.WHITE)
            questionViewModel.getQuestion(tokenInit.toString(), category, difficulty, type)
            load_question.visibility = View.VISIBLE
            question_text.visibility = View.INVISIBLE
            btn_first.visibility = View.INVISIBLE
            btn_second.visibility = View.INVISIBLE
            btn_third.visibility = View.INVISIBLE
            btn_forth.visibility = View.INVISIBLE
            btnDifficulty.visibility = View.INVISIBLE
        }
    }

    private fun onSuccess(data: Result) {
        load_question.visibility = View.GONE
        btnRoll.isClickable = true
        btnRoll.isEnabled = true
        btnRoll.setBackgroundColor(Color.CYAN)
        question_text.text = Html.fromHtml(data.results[0].question, Html.FROM_HTML_MODE_COMPACT)
        question_text.visibility = View.VISIBLE
        btnDifficulty.text = Html.fromHtml(data.results[0].difficulty, Html.FROM_HTML_MODE_COMPACT)

        if(btnDifficulty.text == Html.fromHtml("easy", Html.FROM_HTML_MODE_COMPACT) ){
            btnDifficulty.setBackgroundResource(R.drawable.btn_easy)
        }
        else if(btnDifficulty.text == Html.fromHtml("medium", Html.FROM_HTML_MODE_COMPACT)){
            btnDifficulty.setBackgroundResource(R.drawable.btn_medium)
        }
        else if(btnDifficulty.text == Html.fromHtml("hard", Html.FROM_HTML_MODE_COMPACT)){
            btnDifficulty.setBackgroundResource(R.drawable.btn_hard)
        }

        btnDifficulty.visibility = View.VISIBLE
        if(data.results[0].type == "multiple")
        {
            btn_first.visibility = View.VISIBLE
            btn_second.visibility = View.VISIBLE
            btn_third.visibility = View.VISIBLE
            btn_forth.visibility = View.VISIBLE

            val answerList = mutableListOf<String>()
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
            val booleanList = mutableListOf<String>()
            booleanList.add(data.results[0].correctAnswer)
            booleanList.add(data.results[0].incorrectAnswers[0])

            btn_first.text = Html.fromHtml(booleanList.random(), Html.FROM_HTML_MODE_COMPACT)
            booleanList.remove(btn_first.text.toString())
            btn_second.text = Html.fromHtml(booleanList.random(), Html.FROM_HTML_MODE_COMPACT)
            btn_first.setOnClickListener(this)
            btn_second.setOnClickListener(this)
        }
    }

    override fun onClick(v: View?) {

        val animationWrong = AnimationUtils.loadAnimation(this, R.anim.shake)

        when (v?.id) {
            R.id.btn_first -> {
                btn_second.setBackgroundColor(Color.WHITE)
                btn_third.setBackgroundColor(Color.WHITE)
                btn_forth.setBackgroundColor(Color.WHITE)

                if(btn_first.text == correct){
                    btn_first.setBackgroundColor(Color.GREEN)
                    btn_second.clearAnimation()
                    btn_third.clearAnimation()
                    btn_forth.clearAnimation()

                } else{
                    btn_first.setBackgroundColor(Color.RED)
                    btn_first.startAnimation(animationWrong)
                    btn_second.clearAnimation()
                    btn_third.clearAnimation()
                    btn_forth.clearAnimation()
                }
            }
            R.id.btn_second -> {
                btn_first.setBackgroundColor(Color.WHITE)
                btn_third.setBackgroundColor(Color.WHITE)
                btn_forth.setBackgroundColor(Color.WHITE)

                if(btn_second.text == correct){
                    btn_second.setBackgroundColor(Color.GREEN)
                    btn_first.clearAnimation()
                    btn_third.clearAnimation()
                    btn_forth.clearAnimation()
                } else{
                    btn_second.setBackgroundColor(Color.RED)
                    btn_second.startAnimation(animationWrong)
                    btn_first.clearAnimation()
                    btn_third.clearAnimation()
                    btn_forth.clearAnimation()
                }
            }
            R.id.btn_third -> {
                btn_first.setBackgroundColor(Color.WHITE)
                btn_second.setBackgroundColor(Color.WHITE)
                btn_forth.setBackgroundColor(Color.WHITE)

                if(btn_third.text == correct){
                    btn_third.setBackgroundColor(Color.GREEN)
                    btn_first.clearAnimation()
                    btn_second.clearAnimation()
                    btn_forth.clearAnimation()
                } else {
                    btn_third.setBackgroundColor(Color.RED)
                    btn_third.startAnimation(animationWrong)
                    btn_first.clearAnimation()
                    btn_second.clearAnimation()
                    btn_forth.clearAnimation()
                }
            }
            R.id.btn_forth -> {
                btn_first.setBackgroundColor(Color.WHITE)
                btn_second.setBackgroundColor(Color.WHITE)
                btn_third.setBackgroundColor(Color.WHITE)

                if(btn_forth.text == correct){
                    btn_forth.setBackgroundColor(Color.GREEN)
                    btn_first.clearAnimation()
                    btn_second.clearAnimation()
                    btn_third.clearAnimation()
                } else {
                    btn_forth.setBackgroundColor(Color.RED)
                    btn_forth.startAnimation(animationWrong)
                    btn_first.clearAnimation()
                    btn_second.clearAnimation()
                    btn_third.clearAnimation()
                }
            }
        }
    }
}
