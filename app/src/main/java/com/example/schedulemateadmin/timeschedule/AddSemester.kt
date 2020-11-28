package com.example.schedulemateadmin.timeschedule

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.schedulemateadmin.R
import com.example.schedulemateadmin.SelectUniversity
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.add_semester.*

class AddSemester : AppCompatActivity() {
    lateinit var university: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_semester)

        university = intent.getStringExtra("university")

        registerSemester.setOnClickListener {
            if(semester.text.toString()== "")
                Toast.makeText(this, "학기를 입력하세요.", Toast.LENGTH_LONG).show()
            else if(start.text.toString() == "")
                Toast.makeText(this, "개강일을 입력하세요,", Toast.LENGTH_LONG).show()
            else if(end.text.toString() == "")
                Toast.makeText(this, "종강일을 입력하세요.", Toast.LENGTH_LONG).show()
            else{
                var db = FirebaseDatabase.getInstance().reference.child(university)
                db.child(semester.text.toString()).setValue("")
                db.child("/info/semester/" + semester.text.toString() + "/start").setValue(start.text.toString())
                db.child("/info/semester/" + semester.text.toString() + "/end").setValue(end.text.toString())

                moveTo(Intent(this, MainTimeSchedulePage::class.java))
            }
        }
    }
    fun moveTo(intent:Intent){
        intent.putExtra("university", university)
        startActivity(intent)
    }
}