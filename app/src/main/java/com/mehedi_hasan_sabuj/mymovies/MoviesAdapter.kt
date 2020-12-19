package com.mehedi_hasan_sabuj.mymovies

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop

class MoviesAdapter(
    private var movies: MutableList<Movie>,
    private val onMovieClick: (movie: Movie) -> Unit
) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    fun appendMovies(movies: List<Movie>) {
        this.movies.addAll(movies)
        notifyItemRangeInserted(
            this.movies.size,
            movies.size - 1
        )
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val poster: ImageView = itemView.findViewById(R.id.item_movie_poster)
        private val title: TextView = itemView.findViewById(R.id.home_title)
        private val date: TextView = itemView.findViewById(R.id.home_date)
        private lateinit var rating: RatingBar



        fun bind(movie: Movie) {
            Glide.with(itemView)
                .load("https://image.tmdb.org/t/p/w342${movie.posterPath}")
                .transform(CenterCrop())
                .into(poster)
            val s= movie.title
            Log.d("Length","")
            title.setText(movie.name)
            val temp = title.text.length

            if(temp==0){
                title.setText(movie.title)
            }
            date.setText(movie.releaseDate)
            val temp_date = date.text.length
            if(temp_date==0){
                date.setText(movie.air_date)
            }
            rating = itemView.findViewById(R.id.home_rating)
            rating.rating = (movie.rating)/ 2

            itemView.setOnClickListener { onMovieClick.invoke(movie) }
        }
    }
}