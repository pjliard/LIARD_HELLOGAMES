package com.example.pierr.hellogames


import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.pierr.hellogames.R.id.*
import com.example.pierr.hellogames.entities.Game
import com.example.pierr.hellogames.entities.GameDescription
import com.example.pierr.hellogames.entities.WebServiceInterface
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SecondActivity : AppCompatActivity(), View.OnClickListener {
    var game = GameDescription()

    val baseURL = "https://androidlessonsapi.herokuapp.com/api/"

    val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())

    val retrofit = Retrofit.Builder()
        .baseUrl(baseURL)
        .addConverterFactory(jsonConverter)
        .build()

    val service: WebServiceInterface = retrofit.create(WebServiceInterface::class.java)

    val callback = object : Callback<GameDescription> {
        override fun onFailure(call: Call<GameDescription>?, t: Throwable?) {
            // Code here what happens if calling the WebService fails
            Log.d("TAG", "WebService call failed")
        }
        override fun onResponse(call: Call<GameDescription>?,
                                response: Response<GameDescription>?) {
            // Code here what happens when WebService responds
            if (response != null) {
                Log.d("TAG", response.toString())
                if (response.code() == 200) {
                    // We got our data !
                    val responseData = response.body()
                    if (responseData != null) {
                        game = responseData
                        findViewById<TextView>(R.id.name).text = game.name
                        findViewById<TextView>(R.id.type).text = game.type
                        findViewById<TextView>(R.id.nbplayers).text = game.players.toString()
                        findViewById<TextView>(R.id.year).text = game.year.toString()

                        findViewById<TextView>(R.id.desc).text = game.description_en

                        val image = findViewById<ImageView>(R.id.img)

                        SetImageGame(image)
                        val btnUrl = findViewById<Button>(R.id.info)
                        btnUrl.setOnClickListener(this@SecondActivity)
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val gameId = intent.getIntExtra("gameId", -1)

        service.listofGameDetails(gameId).enqueue(callback)

        Log.d("TAG", "id :")
    }

    private fun SetImageGame(imageView: ImageView)
    {
        when (game.id)
        {
            1 -> imageView.setImageResource(R.drawable.tic_tac_toe)
            2 -> imageView.setImageResource(R.drawable.hangman_game)
            3 -> imageView.setImageResource(R.drawable.sudoku)
            4 -> imageView.setImageResource(R.drawable.battleship)
            5 -> imageView.setImageResource(R.drawable.minesweeper)
            6 -> imageView.setImageResource(R.drawable.game_of_life)
            7 -> imageView.setImageResource(R.drawable.memory)
            8 -> imageView.setImageResource(R.drawable.simon)
            9 -> imageView.setImageResource(R.drawable.jigsaw)
            10 -> imageView.setImageResource(R.drawable.mastermind)
        }

    }


    override fun onClick(clickedView: View?) {

        if (clickedView != null)
        {
            when (clickedView.id)
            {
                R.id.info ->
                {
                    val url = game.url
                    val intent = Intent(Intent.ACTION_VIEW)

                    intent.data = Uri.parse(url)

                    startActivity(intent)
                }
            }
        }
    }
}
