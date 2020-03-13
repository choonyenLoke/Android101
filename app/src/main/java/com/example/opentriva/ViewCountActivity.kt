package com.example.opentriva

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.opentriva.ApiService.ApiServiceInterface
import com.example.opentriva.ApiService.RetrofitService
import com.example.opentriva.Model.Category
import com.example.opentriva.Model.CategoryQuestionCount
import com.example.opentriva.Model.Count
import com.example.opentriva.Model.TriviaCategory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.question_count.*

class ViewCountActivity : AppCompatActivity() {

    private val subscription = CompositeDisposable()
    private val apiService: ApiServiceInterface = RetrofitService.create()
    var countList = mutableListOf<Count>()
    private var countAdapter: CountAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.question_count)
        val actionbar = supportActionBar
        actionbar?.title = "Question Category"
        actionbar?.setDisplayHomeAsUpEnabled(true)
        getAllCategory()
    }

    fun getAllCategory(){
        val catList = mutableListOf<TriviaCategory>()
        val subscribe = apiService.getAllCategory()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                for(category in it.trivia_categories){
                    getAllCount(category.id)
                }

            },{
                error ->
                val msg = error.localizedMessage
                Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
            })
        subscription.add(subscribe)
    }

    fun getAllCount(id: Int){
        val subscribe = apiService.getAllCount(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onCountSuccess(it)
            },{
                error ->
                val msg = error.localizedMessage
                Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
            })
    }

    private fun onCountSuccess(data: Count) {
        countList.add(data)
        countAdapter = CountAdapter(this, countList)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recycler_view.layoutManager = layoutManager
        recycler_view.adapter = countAdapter
        loadingCount.visibility = View.GONE
    }


}
