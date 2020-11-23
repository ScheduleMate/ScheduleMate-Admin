package com.example.schedulemateadmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class MainActivity : AppCompatActivity() {
    val SPLASH_VIEW_TIME: Long = 4000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Handler().postDelayed(Runnable{
            startActivity(Intent(this, SignIn::class.java))
            finish()
        }, SPLASH_VIEW_TIME)

    }
}