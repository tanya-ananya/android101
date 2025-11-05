package com.example.picsum

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import org.json.JSONArray
import cz.msebera.android.httpclient.Header

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: ImageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        adapter = ImageAdapter()
        val rv = findViewById<RecyclerView>(R.id.recyclerView)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter

        findViewById<android.widget.Button>(R.id.getImageBtn).setOnClickListener {
            fetchImages()
        }
    }

    private fun fetchImages() {
        val client = AsyncHttpClient()
        val url = "https://picsum.photos/v2/list?page=1&limit=100"

        client.get(url, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, response: JSONArray?) {
                response?.let { arr ->
                    val list = mutableListOf<ImageItem>()
                    for (i in 0 until arr.length()) {
                        val obj = arr.getJSONObject(i)
                        val item = ImageItem(
                            id = obj.getString("id"),
                            author = obj.getString("author"),
                            downloadUrl = obj.getString("download_url")
                        )
                        list.add(item)
                    }
                    adapter.setItems(list)
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, throwable: Throwable?, errorResponse: JSONArray?) {
                Log.e("Picsum", "Failed to fetch list", throwable)
            }
        })
    }
}