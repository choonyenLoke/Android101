package com.example.opentriva.view

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import com.example.opentriva.R
import com.example.opentriva.model.Category
import com.example.opentriva.model.TriviaCategory
import com.example.opentriva.viewmodel.CategoryViewModel
import kotlinx.android.synthetic.main.configure_question.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var categoryList: List<TriviaCategory>
    private var selectedCategoryId: Int = 0

    private val categoryViewModel: CategoryViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.configure_question)
        populateData()

        view_count.setOnClickListener{
            val intent = Intent(this, ViewCountActivity::class.java)
            startActivity(intent)
        }

        btnNext.setOnClickListener{
            val intent = Intent(this, QuestionActivity::class.java)
            val selectedCategory = spin_category.selectedItem.toString()
            val selectedDifficulty = spin_difficult.selectedItem.toString()
            val selectedType = spin_type.selectedItem.toString()

            for(category in categoryList){
                if(category.name == selectedCategory){
                    selectedCategoryId = category.id
                    break
                }
                else
                {
                    selectedCategoryId = 0
                }
            }
            intent.putExtra("Category", selectedCategoryId)
            intent.putExtra("Difficulty", selectedDifficulty)
            intent.putExtra("Type", selectedType)
            startActivity(intent)
        }
        categoryViewModel.getAllCategory()
        categoryViewModel.catList.observe(this, Observer {
            onCategorySuccess(it)
        })

    }

    private fun populateData(){
        val spinDifficult = spin_difficult
        val spinType = spin_type

        val difficult = arrayOf("Default", "Easy", "Medium", "Hard")
        val type = arrayOf("Default", "Multiple", "Boolean")

        val difficultAdapter = ArrayAdapter(this,
            R.layout.custom_spinner, difficult)
        val typeAdapter = ArrayAdapter(this,
            R.layout.custom_spinner, type)

        spinDifficult.adapter = difficultAdapter
        spinType.adapter = typeAdapter
    }

    private fun onCategorySuccess(category: Category) {
        val spinCat = spin_category
        categoryList = category.triviaCategories
        val arrayCategory = mutableListOf("Default")
        for(cat in categoryList){
            arrayCategory.add(cat.name)
        }
        val catAdapter = ArrayAdapter(this,
            R.layout.custom_spinner, arrayCategory)
        spinCat.adapter = catAdapter
        loading.visibility = View.GONE
        btnNext.setBackgroundColor(Color.CYAN)
        btnNext.isClickable = true
        btnNext.isEnabled = true
    }
}
