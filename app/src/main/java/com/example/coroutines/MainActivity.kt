package com.example.coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    lateinit var tv_change: TextView
    lateinit var download_job:Job
    val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var start_download = findViewById<Button>(R.id.startDownload)
        var cancel_download = findViewById<Button>(R.id.cancel)
        tv_change = findViewById(R.id.textView)


        start_download.setOnClickListener {
            val timeout = 20000L
            GlobalScope.launch {
                download_job = launch {
                    startDownload(timeout)
                }
            }
        }

        cancel_download.setOnClickListener {
            GlobalScope.launch {
                Log.d(TAG,"Cancelled on thread ${Thread.currentThread().name}")
                download_job.cancel()
                withContext(Dispatchers.Main)
                {
                    tv_change.text = "Download Cancelled"
                }
            }
        }

    }

    suspend fun startDownload(time: Long)
    {
        withContext(Dispatchers.Main)
        {
            tv_change.text = "Download started"
        }
        Log.d(TAG,"Started downloading on thread ${Thread.currentThread().name}")
        delay(time)
    }

}