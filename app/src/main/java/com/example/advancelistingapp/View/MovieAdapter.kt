package com.example.advancelistingapp.View

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.advancelistingapp.Model.Movie
import com.example.advancelistingapp.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_view.view.*

class MovieAdapter(private val mContext: Context, private var movieList: MutableList<Movie>, private val mListener: MovieListener)
    :RecyclerView.Adapter<MovieAdapter.ViewHolder>()
{
    private var context = mContext

    interface MovieListener{
        fun onItemClick(movie: Movie)
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(movies: Movie, mListener: MovieListener){
            itemView.setOnClickListener{mListener.onItemClick(movies)}
            itemView.movie_title.text = movies.title
            itemView.movie_plot.text = movies.plot
            Picasso.with(context)
                .load(movies.poster)
                .fit()
                .centerInside()
                .error(R.drawable.broken)
                .placeholder(R.drawable.load)
                .into(itemView.movie_img)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdapter.ViewHolder {
        val movieView = LayoutInflater.from(context)
            .inflate(R.layout.card_view, parent, false)
        return ViewHolder(movieView)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: MovieAdapter.ViewHolder, position: Int) {
        movieList[position].let { holder.bind(it, mListener) }
    }

}