package com.example.task2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ItemAdapter internal constructor(context: Context):RecyclerView.Adapter<ItemAdapter.ViewHolder>()
{
    private val context: Context = context
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    var onItemClick:((ItemClass) -> Unit)? = null

    private var items = emptyList<ItemClass>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val itemView = inflater.inflate(R.layout.cardview, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.title.text = item.itemTitle
        holder.sub_title.text = item.itemSub
        holder.desc.text = item.itemDesc
        val imgUrl: String = item.itemUrl
        Picasso.with(context)
            .load(imgUrl)
            .placeholder(R.drawable.load)
            .resize(200,200)
            .centerCrop()
            .error(R.drawable.broken)
            .into(holder.imageView)

    }
    internal fun setItem(items: List<ItemClass>){
        this.items = items
        notifyDataSetChanged()
    }


    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)
    {
        var title: TextView
        var sub_title: TextView
        var desc: TextView
        var imageView: ImageView

        init {
            title = itemView.findViewById(R.id.item_title)
            sub_title = itemView.findViewById(R.id.subTitle)
            desc = itemView.findViewById(R.id.item_desc)
            imageView = itemView.findViewById(R.id.item_img)

            itemView.setOnClickListener {
                onItemClick?.invoke(items[adapterPosition])
            }
        }
    }
}