package com.example.schedulemateadmin

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.schedulemateadmin.declare.MainDeclarePage
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.sign_in.*

class SignIn : AppCompatActivity() {
    private val mAuth:FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_in)


        password.setOnKeyListener(object : View.OnKeyListener{
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if(keyCode == KeyEvent.KEYCODE_ENTER){
                    signIn.callOnClick()
                    return true
                }
                return false
            }
        })

        signIn.setOnClickListener {
            if(user.text.toString() == "")
                Toast.makeText(this, "이메일을 입력하세요.", Toast.LENGTH_LONG).show()
            else if(password.text.toString() == "")
                Toast.makeText(this, "비밀번호를 입력하세요.", Toast.LENGTH_LONG).show()
            else {
                mAuth.signInWithEmailAndPassword(user.text.toString(), password.text.toString())
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            startActivity(Intent(this, SelectUniversity::class.java))
                            Toast.makeText(applicationContext, "로그인 성공", Toast.LENGTH_SHORT).show()
                        }
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "이메일이나 비밀번호를 다시 입력하세요.", Toast.LENGTH_LONG).show()
                    }

            }
        }
    }
}