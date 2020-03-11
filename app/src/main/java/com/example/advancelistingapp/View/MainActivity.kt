package com.example.advancelistingapp.View

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.getSystemService
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.advancelistingapp.DependencyInjection.DaggerMovieComponent
import com.example.advancelistingapp.DependencyInjection.MovieModule
import com.example.advancelistingapp.Model.Movie
import com.example.advancelistingapp.Model.SearchResult
import com.example.advancelistingapp.Presenter.MovieContract
import com.example.advancelistingapp.R
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MovieAdapter.MovieListener, MovieContract.View {

    @Inject
    lateinit var presenter: MovieContract.Presenter

    lateinit var movieList: MutableList<Movie>
    private var movieAdapter: MovieAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        injectDependency()
        presenter.attach(this)
        welc_msg.visibility = View.VISIBLE
        initView()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detach()

    }

    private fun initView(){
        btnSearch.setOnClickListener{
            presenter.unsubscribe()
            progress_bar.visibility = View.VISIBLE
            recyclerview.visibility = View.INVISIBLE
            welc_msg.visibility = View.GONE
            error_msg.visibility = View.INVISIBLE
            hideKeyboard(it)
            val key = searchBar.text.toString()
            movieList = mutableListOf()
            if(key.isEmpty()){
                Toast.makeText(this, "Search Bar cannot be empty!!!", Toast.LENGTH_LONG).show()
            }
            else{
                presenter.getAllMovie(key)
                searchBar.setText("")
            }
        }
    }

    fun Context.hideKeyboard(view:View){
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun injectDependency(){
        val movieComponent = DaggerMovieComponent.builder()
            .movieModule(MovieModule(this))
            .build()
        movieComponent.inject(this)
    }

    override fun onSuccess(data: SearchResult) {
        val listMovie = data.search
        for(movie in listMovie){
            presenter.getFullMovie(movie.imdbID)
        }
    }

    override fun onError(msg: String?) {
        progress_bar.visibility = View.GONE
        welc_msg.visibility = View.GONE
        error_msg.visibility = View.VISIBLE
    }

    override fun onFullSuccess(data: Movie) {
        movieList.add(data)
        movieAdapter = MovieAdapter(this, movieList, this)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerview.layoutManager = layoutManager
        recyclerview.adapter = movieAdapter
        progress_bar.visibility = View.GONE
        recyclerview.visibility = View.VISIBLE

    }

    override fun onFullError(msg: String?) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    override fun onItemClick(movie: Movie) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("movieImg", movie.poster)
        intent.putExtra("movieTitle", movie.title)
        intent.putExtra("movieRating", movie.imdbRating)
        intent.putExtra("movieGenre", movie.genre)
        intent.putExtra("movieVotes", movie.imdbVotes)
        intent.putExtra("movieDirectors", movie.director)
        intent.putExtra("movieActors", movie.actors)
        intent.putExtra("movieRelease", movie.released)
        intent.putExtra("movieLanguage", movie.language)
        intent.putExtra("movieRunTime", movie.runtime)
        intent.putExtra("movieType", movie.type)
        intent.putExtra("moviePlot", movie.plot)
        startActivity(intent)
    }
}

