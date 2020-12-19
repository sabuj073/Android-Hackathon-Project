package com.mehedi_hasan_sabuj.mymovies

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface Api {

    @GET("discover/movie")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = "1a97f3b8d5deee1d649c0025f3acf75c",
        @Query("page") page: Int,
        @Query("primary_release_year ") release_date: String = "2020",
        @Query("sort_by ") sort_by: String = "vote_average.desc"
    ): Call<GetMoviesResponse>

    @GET("discover/tv")
    fun getTopRatedTvSeries(
        @Query("api_key") apiKey: String = "1a97f3b8d5deee1d649c0025f3acf75c",
        @Query("page") page: Int,
        @Query("primary_release_year ") release_date: String = "2020",
        @Query("sort_by ") sort_by: String = "vote_average.desc"
    ): Call<GetMoviesResponse>

    @GET("trending/all/week")
    fun getUpcomingMovies(
        @Query("api_key") apiKey: String = "1a97f3b8d5deee1d649c0025f3acf75c",
        @Query("page") page: Int
    ): Call<GetMoviesResponse>
}