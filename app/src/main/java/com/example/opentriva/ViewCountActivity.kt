package com.example.opentriva

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.opentriva.apiservice.ApiServiceInterface
import com.example.opentriva.apiservice.RetrofitService
import com.example.opentriva.model.*
import com.example.opentriva.viewmodel.QuestionViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.question_count.*

class ViewCountActivity : AppCompatActivity() {

    private val subscription = CompositeDisposable()
    private val apiService: ApiServiceInterface = RetrofitService.create()
    var countList = mutableListOf<Count>()
    var categoryList = mutableListOf<TriviaCategory>()
    private var countAdapter: CountAdapter? = null

    private lateinit var questionViewModel: QuestionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.question_count)
        val actionbar = supportActionBar
        actionbar?.title = "Question Category"
        actionbar?.setDisplayHomeAsUpEnabled(true)

        questionViewModel = ViewModelProvider(this).get(QuestionViewModel::class.java)

        questionViewModel.getAllCategory()
        questionViewModel.catList.observe(this, Observer {
            for(category in it.triviaCategories){
                questionViewModel.getAllCount(category.id)
                onCategorySuccess(category)
            }
        })
        questionViewModel.countList.observe(this, Observer {
            onCountSuccess(it)
            countAdapter = CountAdapter(this, countList, categoryList)
            val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
            recycler_view.layoutManager = layoutManager
            recycler_view.adapter = countAdapter
            loadingCount.visibility = View.GONE
        })
    }

    private fun onCategorySuccess(data: TriviaCategory) {
        categoryList.add(data)
    }

    private fun onCountSuccess(data: Count) {
        countList.add(data)
    }

}
