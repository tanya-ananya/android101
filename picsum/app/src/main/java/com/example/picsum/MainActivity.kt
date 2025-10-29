package com.example.picsum

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.BinaryHttpResponseHandler
import android.graphics.BitmapFactory
import android.widget.ImageView
import android.widget.TextView
import com.loopj.android.http.JsonHttpResponseHandler
import com.bumptech.glide.Glide


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        findViewById<android.widget.Button>(R.id.getImageBtn).setOnClickListener {
            fetchRandomImage()
        }
    }

    private fun fetchRandomImage() {
        val client = AsyncHttpClient()
        val url = "https://picsum.photos/v2/list?page=1&limit=100"

        client.get(url, object : JsonHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out cz.msebera.android.httpclient.Header>?,
                response: org.json.JSONArray?
            ) {
                response?.let {
                    val randomIndex = (0 until it.length()).random()
                    val imageObj = it.getJSONObject(randomIndex)

                    val author = imageObj.getString("author")
                    val imageUrl = imageObj.getString("download_url")
                    val id = imageObj.getString("id")

                    findViewById<TextView>(R.id.imageInfo).text =
                        "Author: $author\nID: $id"

                    Glide.with(this@MainActivity)
                        .load(imageUrl)
                        .into(findViewById(R.id.randImage))
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out cz.msebera.android.httpclient.Header>?,
                throwable: Throwable?,
                errorResponse: org.json.JSONArray?
            ) {
                Log.e("Picsum", "Failed to fetch image info", throwable)
            }
        })
    }
}
