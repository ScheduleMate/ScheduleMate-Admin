package com.example.schedulemateadmin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.schedulemateadmin.declare.MainDeclarePage

class SignIn: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_in)

        val signIn = findViewById<Button>(R.id.signIn)
        signIn.setOnClickListener {
            startActivity(Intent(this, MainDeclarePage::class.java))
        }
    }
}