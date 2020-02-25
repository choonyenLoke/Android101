package com.example.task1

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class RecyclerAdapter(val itemList: ArrayList<Item>) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(){


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var itemImage: ImageView
        var itemTitle: TextView
        var itemSubTitle: TextView
        var itemDesc: TextView


        fun bindItem(item: Item) {
            itemImage.setImageResource(item.mImageRes)
            itemImage.tag = item.mImageRes
            itemTitle.text = item.mTitle
            itemSubTitle.text = item.mSub
            itemDesc.text = item.mDesc
        }


        init {
            itemImage = itemView.findViewById(R.id.item_img)
            itemTitle = itemView.findViewById(R.id.item_title)
            itemSubTitle = itemView.findViewById(R.id.subTitle)
            itemDesc = itemView.findViewById(R.id.item_desc)

            itemView.setOnClickListener{

                    val title = itemTitle.text.toString()
                    val subTitle = itemSubTitle.text.toString()
                    val desc = itemDesc.text.toString()
                    val imageRes = itemImage.tag.toString().toInt()

                    val item = Item(imageRes, title, subTitle, desc)

                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(ITEM, item)

                    itemView.context.startActivity(intent)
                }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_view, parent, false)
        return ViewHolder(v)
    }
     override fun onBindViewHolder(viewHolder: ViewHolder, i: Int){
         viewHolder.bindItem(itemList[i])

     }
     override fun getItemCount(): Int{
         return itemList.size
     }
     companion object {
         const val ITEM = "item"
     }
}

