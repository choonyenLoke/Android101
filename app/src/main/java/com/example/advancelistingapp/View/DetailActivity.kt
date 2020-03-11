package com.example.advancelistingapp.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannedString
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import com.example.advancelistingapp.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val actionbar = supportActionBar
        actionbar?.title = "Movie Details"
        actionbar?.setDisplayHomeAsUpEnabled(true)

        intent?.let {
            val movieImg = it.getStringExtra("movieImg")
            val title = it.getStringExtra("movieTitle")
            val rating = it.getStringExtra("movieRating")
            val genre = it.getStringExtra("movieGenre")
            val votes = it.getStringExtra("movieVotes")
            val director = it.getStringExtra("movieDirectors")
            val actor = it.getStringExtra("movieActors")
            val release = it.getStringExtra("movieRelease")
            val language = it.getStringExtra("movieLanguage")
            val runTime = it.getStringExtra("movieRunTime")
            val type = it.getStringExtra("movieType")
            val plot = it.getStringExtra("moviePlot")

            val rate: SpannedString = buildSpannedString {
                append(rating)
                append(getString(R.string.percen))
            }

            val vote: SpannedString = buildSpannedString {
                bold {
                    append(getString(R.string.b_vote))
                }
                append(votes)
            }

            val directors: SpannedString = buildSpannedString {
                bold{
                    append(getString(R.string.b_director))
                }
                append(director)
            }
            val actors: SpannedString = buildSpannedString {
                bold {
                    append(getString(R.string.b_actors))
                }
                append(actor)
            }
            val date: SpannedString = buildSpannedString {
                bold {
                    append(getString(R.string.b_date))
                }
                append(release)
            }
            val lang: SpannedString = buildSpannedString {
                bold {
                    append(getString(R.string.b_lang))
                }
                append(language)
            }
            val runtime: SpannedString = buildSpannedString {
                bold {
                    append(getString(R.string.b_run))
                }
                append(runTime)
            }
            val types: SpannedString = buildSpannedString {
                bold {
                    append(getString(R.string.b_type))
                }
                append(type)
            }

            Picasso.with(this)
                .load(movieImg)
                .fit()
                .placeholder(R.drawable.load)
                .error(R.drawable.broken)
                .into(movieImage)
            movieTitle.text = title
            movieRating.text = rate
            movieGenre.text = genre
            movieVotes.text = vote
            movieDirector.text = directors
            movieActors.text = actors
            movieRelease.text = date
            movieLanguage.text = lang
            movieRunTime.text = runtime
            movieType.text = types
            moviePlot.text = plot
        }
    }
}
