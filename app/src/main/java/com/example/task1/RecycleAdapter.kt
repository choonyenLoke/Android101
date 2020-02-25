package com.example.task1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(itemList: ArrayList<Item>) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private lateinit var context: Context
    var itemList = itemList
    var onItemClick: ((Item) -> Unit)? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var itemImage: ImageView
        var itemTitle: TextView
        var itemSubTitle: TextView
        var itemDesc: TextView

        init {
            itemImage = itemView.findViewById(R.id.item_img)
            itemTitle = itemView.findViewById(R.id.item_title)
            itemSubTitle = itemView.findViewById(R.id.subTitle)
            itemDesc = itemView.findViewById(R.id.item_desc)
            itemView.setOnClickListener{
                onItemClick!!.invoke(itemList[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val v: View = inflater.inflate(R.layout.card_view, parent, false)
        return ViewHolder(v)
    }
    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int){
        val item = itemList[i]

        viewHolder.itemImage.setImageResource(item.mImageRes)
        viewHolder.itemImage.tag = item.mImageRes
        viewHolder.itemTitle.text = item.mTitle
        viewHolder.itemSubTitle.text = item.mSub
        viewHolder.itemDesc.text = item.mDesc
    }
    override fun getItemCount(): Int{
        return itemList.size
    }
    companion object {
        const val ITEM = "item"
    }

}