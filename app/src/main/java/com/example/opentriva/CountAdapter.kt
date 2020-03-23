package com.example.opentriva

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.opentriva.model.Count
import com.example.opentriva.model.TriviaCategory
import kotlinx.android.synthetic.main.card_view.view.*

class CountAdapter(private val mContext: Context, private var countList: MutableList<Count>, private var categoryList: MutableList<TriviaCategory>)
    :RecyclerView.Adapter<CountAdapter.ViewHolder>()
{

    private var context = mContext

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(count: Count){
            for(item in categoryList){
                if(count.categoryId.compareTo(item.id) == 0){
                    itemView.category_label.text = item.name
                }
            }
            itemView.easy_text.text = context.getString(R.string.easy) + " " +count.categoryQuestionCount.easyCount
            itemView.medium_text.text = context.getString(R.string.medium) + " " + count.categoryQuestionCount.mediumCount.toString()
            itemView.hard_text.text = context.getString(R.string.hard) + " " + count.categoryQuestionCount.hardCount.toString()
            itemView.totalQuestion.text = count.categoryQuestionCount.totalCount.toString()
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