package com.example.pierr.hellogames

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import com.example.pierr.hellogames.R.id.*
import com.example.pierr.hellogames.entities.Game
import com.example.pierr.hellogames.entities.WebServiceInterface
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    val gameList = arrayListOf<Game>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val baseURL = "https://androidlessonsapi.herokuapp.com/api/"

        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())

        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(jsonConverter)
            .build()

        val service: WebServiceInterface = retrofit.create(WebServiceInterface ::class.java)

        val callback = object : Callback<List<Game>> {
            override fun onFailure(call: Call<List<Game>>?, t: Throwable?) {
                // Code here what happens if calling the WebService fails
                Log.d("TAG", "WebService call failed")
            }
            override fun onResponse(call: Call<List<Game>>?,
                                    response: Response<List<Game>>?) {
                // Code here what happens when WebService responds
                if (response != null) {
                    if (response.code() == 200) {
                        // We got our data !
                        val responseData = response.body()
                        if (responseData != null) {
                            gameList.addAll(responseData)

                            SetImageGame(findViewById(R.id.jeux1), 0)
                            SetImageGame(findViewById(R.id.jeux2), 1)
                            SetImageGame(findViewById(R.id.jeux3), 2)
                            SetImageGame(findViewById(R.id.jeux4), 3)

                            findViewById<ImageButton>(jeux1).setOnClickListener{
                                val explicitIntent = Intent(this@MainActivity, SecondActivity::class.java)
                                explicitIntent.putExtra("gameId", gameList[0].id)
                                startActivity(explicitIntent)
                            }

                            findViewById<ImageButton>(jeux2).setOnClickListener{
                                val explicitIntent = Intent(this@MainActivity, SecondActivity::class.java)
                                explicitIntent.putExtra("gameId", gameList[1].id)
                                startActivity(explicitIntent)
                            }

                            findViewById<ImageButton>(jeux3).setOnClickListener{
                                val explicitIntent = Intent(this@MainActivity, SecondActivity::class.java)
                                explicitIntent.putExtra("gameId", gameList[2].id)
                                startActivity(explicitIntent)
                            }

                            findViewById<ImageButton>(jeux4).setOnClickListener{
                                val explicitIntent = Intent(this@MainActivity, SecondActivity::class.java)
                                explicitIntent.putExtra("gameId", gameList[3].id)
                                startActivity(explicitIntent)
                            }



                            Log.d("TAG", "WebService success : " + gameList.size)
                        }
                    }
                }
            }
        }
        service.listofGame().enqueue(callback)

    }

    private fun SetImageGame(imageButton: ImageButton, pos: Int) {
        when (gameList[pos].id) {
            1 -> imageButton.setImageResource(R.drawable.tic_tac_toe)
            2 -> imageButton.setImageResource(R.drawable.hangman_game)
            3 -> imageButton.setImageResource(R.drawable.sudoku)
            4 -> imageButton.setImageResource(R.drawable.battleship)
            5 -> imageButton.setImageResource(R.drawable.minesweeper)
            6 -> imageButton.setImageResource(R.drawable.game_of_life)
            7 -> imageButton.setImageResource(R.drawable.memory)
            8 -> imageButton.setImageResource(R.drawable.simon)
            9 -> imageButton.setImageResource(R.drawable.jigsaw)
            10 -> imageButton.setImageResource(R.drawable.mastermind)
        }
    }

}
