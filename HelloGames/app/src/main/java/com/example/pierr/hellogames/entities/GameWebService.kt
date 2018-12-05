package com.example.pierr.hellogames.entities

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServiceInterface {
    @GET("game/list")

    fun listofGame(): Call<List<Game>>

    @GET("game/details")

    fun listofGameDetails(@Query("game_id") gameId: Int): Call<GameDescription>

}