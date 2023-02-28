package hr.algebra.retrofitandco.model

import com.squareup.moshi.Json

data class Joke (
    @field:Json( name="created_at" ) val createdAt : String,
    @field:Json( name="icon_url" )   val iconURL   : String,
    @field:Json( name="id" )         val id        : String,
    @field:Json( name="updated_at" ) val updatedAt : String,
    @field:Json( name="url" )        val url       : String,
    @field:Json( name="value" )      val value     : String

    //toString()
    //equals( Object )
)