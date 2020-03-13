package com.example.opentriva

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.opentriva.ApiService.ApiServiceInterface
import com.example.opentriva.ApiService.RetrofitService
import com.example.opentriva.Model.Category
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.configure_question.*

class MainActivity : AppCompatActivity() {

    private val subscription = CompositeDisposable()
    private val apiService: ApiServiceInterface = RetrofitService.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.configure_question)
        populateData()
        getAllCategory()

        view_count.setOnClickListener{
            val intent = Intent(this, ViewCountActivity::class.java)
            startActivity(intent)
        }
    }

    fun populateData(){
        val spinDifficult = spin_difficult
        val spinType = spin_type

        val difficult = arrayOf("Default", "Easy", "Medium", "Hard")
        val type = arrayOf("Default", "Multiple", "Boolean")

        val difficultAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, difficult)
        val typeAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, type)

        spinDifficult.adapter = difficultAdapter
        spinType.adapter = typeAdapter


    }

    private fun getAllCategory(){
        val response = apiService.getAllCategory()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("Domain", it.toString())
                onCategorySuccess(it)
            },{ error ->
                val msg = error.localizedMessage
                Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
            })
        subscription.add(response)
    }

    private fun onCategorySuccess(category: Category) {
        val spinCat = spin_category
        val categoryList = category.trivia_categories
        val arrayCategory = mutableListOf("Default")
        for(cat in categoryList){
            arrayCategory.add(cat.name)
        }
        val catAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayCategory)
        spinCat.adapter = catAdapter
        loading.visibility = View.GONE
        btnNext.setBackgroundColor(resources.getColor(R.color.btnRed))
    }
}

