package com.example.flixster

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import org.json.JSONArray

@Parcelize
data class Movie(
    val movieID: Int,
    val title: String,
    val overview: String,
    val voteAverage: Double,
    private val posterPath: String
) : Parcelable {
    @IgnoredOnParcel
    val posterImageUrl = "https://image.tmdb.org/t/p/w342/$posterPath"
    companion object {
        fun fromJsonArray(movieJsonArray: JSONArray, isPortraitOrientation: Boolean): List<Movie> {
            val movies = mutableListOf<Movie>()
            val posterJsonField : String
            if (isPortraitOrientation) {
                posterJsonField = "poster_path"
            } else {
                posterJsonField = "backdrop_path"
            }
            for (i in 0 until movieJsonArray.length()) {
                val movieJson = movieJsonArray.getJSONObject(i)
                movies.add(
                    Movie(
                        movieJson.getInt("id"),
                        movieJson.getString("title"),
                        movieJson.getString("overview"),
                        movieJson.getDouble("vote_average"),
                        movieJson.getString(posterJsonField)
                    )
                )
            }
            return movies
        }
    }
}