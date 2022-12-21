package com.denis.testtask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.denis.testtask.databinding.ActivityMainBinding
import okio.IOException
import retrofit2.HttpException

const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var commentAdapter: CommentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()

        lifecycleScope.launchWhenCreated {
            val response = try {
                RetrofitInstance.api.getComments()
            } catch (e: IOException) {
                Log.e(TAG, "IOException")
                return@launchWhenCreated
            } catch (e: HttpException) {
                Log.e(TAG, "HttpException")
                return@launchWhenCreated
            }
            if (response.isSuccessful && response.body() != null) {
                commentAdapter.comments = response.body()!!
            } else {
                Log.e(TAG, "Response not successful")
            }
        }
    }
    private fun setupRecyclerView() = binding.rvComments.apply {
        commentAdapter = CommentAdapter()
        adapter = commentAdapter
        layoutManager = LinearLayoutManager(this@MainActivity)
    }
}