package com.example.opentriva

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.opentriva.Model.Count
import kotlinx.android.synthetic.main.card_view.view.*

class CountAdapter(private val mContext: Context, private var countList: MutableList<Count>)
    :RecyclerView.Adapter<CountAdapter.ViewHolder>()
{

    private var context = mContext

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(count: Count){
            itemView.easy_text.text = count.category_question_count.total_easy_question_count.toString()
            itemView.medium_text.text = count.category_question_count.total_medium_question_count.toString()
            itemView.hard_text.text = count.category_question_count.total_hard_question_count.toString()
            itemView.totalQuestion.text = count.category_question_count.total_question_count.toString()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountAdapter.ViewHolder {
        val countView = LayoutInflater.from(context)
            .inflate(R.layout.card_view, parent, false)
        return ViewHolder(countView)
    }

    override fun getItemCount(): Int {
        return countList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        countList[position].let { holder.bind(it) }
    }


}