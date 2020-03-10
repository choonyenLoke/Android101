package com.example.advancelistingapp.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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
        initView()
    }

    private fun initView(){
        btnSearch.setOnClickListener{
            movieList = mutableListOf()
            val key = searchBar.text.toString()
            if(key.isEmpty()){
                Toast.makeText(this, "Search Bar cannot be empty!!!", Toast.LENGTH_LONG).show()
            }
            else{
                presenter.getAllMovie(key)
            }
        }
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
        Toast.makeText(this, "Movie Not Found!!!", Toast.LENGTH_LONG).show()
    }

    override fun onFullSuccess(data: Movie) {
        movieList.add(data)
        movieAdapter = MovieAdapter(this, movieList, this)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerview.layoutManager = layoutManager
        recyclerview.adapter = movieAdapter
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

