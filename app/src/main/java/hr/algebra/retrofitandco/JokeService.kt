package hr.algebra.retrofitandco


import hr.algebra.retrofitandco.model.Joke
import io.reactivex.Observable
import retrofit2.http.GET

interface JokeService {

    @GET( "jokes/random" )
    fun getNewJoke( ) : Observable< Joke >
}