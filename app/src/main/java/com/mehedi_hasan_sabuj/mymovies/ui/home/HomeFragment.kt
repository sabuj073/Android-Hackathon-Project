package com.mehedi_hasan_sabuj.mymovies.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mehedi_hasan_sabuj.mymovies.*

class HomeFragment : Fragment() {

  private lateinit var homeViewModel: HomeViewModel
  private lateinit var popularMovies: RecyclerView
  private lateinit var popularMoviesAdapter: MoviesAdapter
  private lateinit var popularMoviesLayoutMgr: LinearLayoutManager

  private var popularMoviesPage = 1

  private lateinit var topRatedMovies: RecyclerView
  private lateinit var topRatedMoviesAdapter: MoviesAdapter
  private lateinit var topRatedMoviesLayoutMgr: LinearLayoutManager

  private var topRatedMoviesPage = 1

  private lateinit var upcomingMovies: RecyclerView
  private lateinit var upcomingMoviesAdapter: MoviesAdapter
  private lateinit var upcomingMoviesLayoutMgr: LinearLayoutManager

  private var upcomingMoviesPage = 1

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
    val root = inflater.inflate(R.layout.fragment_home, container, false)
    popularMovies = root.findViewById(R.id.popular_movies)
    popularMoviesLayoutMgr = LinearLayoutManager(
      activity,
      LinearLayoutManager.HORIZONTAL,
      false
    )
    popularMovies.layoutManager = popularMoviesLayoutMgr
    popularMoviesAdapter = MoviesAdapter(mutableListOf()) { movie -> showMovieDetails(movie) }
    popularMovies.adapter = popularMoviesAdapter

    topRatedMovies = root.findViewById(R.id.top_rated_movies)
    topRatedMoviesLayoutMgr = LinearLayoutManager(
      activity,
      LinearLayoutManager.HORIZONTAL,
      false
    )
    topRatedMovies.layoutManager = topRatedMoviesLayoutMgr
    topRatedMoviesAdapter = MoviesAdapter(mutableListOf()) { movie -> showMovieDetails(movie) }
    topRatedMovies.adapter = topRatedMoviesAdapter

    upcomingMovies = root.findViewById(R.id.upcoming_movies)
    upcomingMoviesLayoutMgr = LinearLayoutManager(
      activity,
      LinearLayoutManager.HORIZONTAL,
      false
    )
    upcomingMovies.layoutManager = upcomingMoviesLayoutMgr
    upcomingMoviesAdapter = MoviesAdapter(mutableListOf()) { movie -> showMovieDetails(movie) }
    upcomingMovies.adapter = upcomingMoviesAdapter

    getPopularMovies()
    getTopRatedTvSeries()
    getUpcomingMovies()
    return root
  }
  private fun getPopularMovies() {
    MoviesRepository.getPopularMovies(
      popularMoviesPage,
      ::onPopularMoviesFetched,
      ::onError
    )
  }
  private fun onPopularMoviesFetched(movies: List<Movie>) {
    popularMoviesAdapter.appendMovies(movies)
    attachPopularMoviesOnScrollListener()
  }

  private fun attachPopularMoviesOnScrollListener() {
    popularMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
      override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val totalItemCount = popularMoviesLayoutMgr.itemCount
        val visibleItemCount = popularMoviesLayoutMgr.childCount
        val firstVisibleItem = popularMoviesLayoutMgr.findFirstVisibleItemPosition()

        if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
          popularMovies.removeOnScrollListener(this)
          popularMoviesPage++
          getPopularMovies()
        }
      }
    })
  }

  private fun getTopRatedTvSeries() {
    MoviesRepository.getTopRatedTvSeries(
      topRatedMoviesPage,
      ::onTopRatedMoviesFetched,
      ::onError
    )
  }

  private fun attachTopRatedMoviesOnScrollListener() {
    topRatedMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
      override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val totalItemCount = topRatedMoviesLayoutMgr.itemCount
        val visibleItemCount = topRatedMoviesLayoutMgr.childCount
        val firstVisibleItem = topRatedMoviesLayoutMgr.findFirstVisibleItemPosition()

        if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
          topRatedMovies.removeOnScrollListener(this)
          topRatedMoviesPage++
          getTopRatedTvSeries()
        }
      }
    })
  }

  private fun onTopRatedMoviesFetched(movies: List<Movie>) {
    topRatedMoviesAdapter.appendMovies(movies)
    attachTopRatedMoviesOnScrollListener()
  }

  private fun getUpcomingMovies() {
    MoviesRepository.getUpcomingMovies(
      upcomingMoviesPage,
      ::onUpcomingMoviesFetched,
      ::onError
    )
  }

  private fun attachUpcomingMoviesOnScrollListener() {
    upcomingMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
      override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val totalItemCount = upcomingMoviesLayoutMgr.itemCount
        val visibleItemCount = upcomingMoviesLayoutMgr.childCount
        val firstVisibleItem = upcomingMoviesLayoutMgr.findFirstVisibleItemPosition()

        if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
          upcomingMovies.removeOnScrollListener(this)
          upcomingMoviesPage++
          getUpcomingMovies()
        }
      }
    })
  }

  private fun onUpcomingMoviesFetched(movies: List<Movie>) {
    upcomingMoviesAdapter.appendMovies(movies)
    attachUpcomingMoviesOnScrollListener()
  }

  private fun showMovieDetails(movie: Movie) {
    val intent = Intent (activity, MovieDetailsActivity::class.java)

    intent.putExtra(MOVIE_BACKDROP, movie.backdropPath)
    intent.putExtra(MOVIE_POSTER, movie.posterPath)
    intent.putExtra(MOVIE_TITLE, movie.title)
    intent.putExtra(MOVIE_RATING, movie.rating)
    intent.putExtra(MOVIE_RELEASE_DATE, movie.releaseDate)
    intent.putExtra(MOVIE_OVERVIEW, movie.overview)
    intent.putExtra(MOVIE_NAME, movie.name)
    intent.putExtra(MOVIE_AIR_DATE, movie.air_date)
    startActivity(intent)
  }

  private fun onError() {
  }
}