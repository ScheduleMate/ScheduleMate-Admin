package com.example.schedulemateadmin.timeschedule

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.schedulemateadmin.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.add_lecture.*
import kotlinx.android.synthetic.main.comment_contents.*
import kotlinx.android.synthetic.main.timeschedule_recyclerview.*

class AddLecture : AppCompatActivity() {
    var newLecture = NewLecture("", "", "", "", "", "")
    lateinit var semester :String
    lateinit var university :String
    lateinit var root :DatabaseReference

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_lecture)

        semester = intent.getStringExtra("semester")
        university = intent.getStringExtra("university")
        root = FirebaseDatabase.getInstance().reference.child(university)


        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val actionBar = getSupportActionBar()
        actionBar!!.setDisplayShowTitleEnabled(false)

        var dayArray = resources.getStringArray(R.array.days)
        var dayAdpater = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dayArray)
        day.adapter = dayAdpater
        day.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                newLecture.day = day.selectedItem.toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        var hourArray = resources.getStringArray(R.array.hours)
        var firstHourAdapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, hourArray)
        var secondHourAdapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, hourArray)
        firstHour.adapter = firstHourAdapter
        secondHour.adapter = secondHourAdapter
        firstHour.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                newLecture.startHour = firstHour.selectedItem.toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        secondHour.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                newLecture.endHour = secondHour.selectedItem.toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        var minuteArray = resources.getStringArray(R.array.minutes)
        var firstMinuteAdapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, minuteArray)
        var secondMinuteAdapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, minuteArray)
        firstMinutes.adapter = firstMinuteAdapter
        secondMinutes.adapter = secondMinuteAdapter
        firstMinutes.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                newLecture.startMinute = firstMinutes.selectedItem.toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        secondMinutes.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                newLecture.endMinute = secondMinutes.selectedItem.toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        var typeArray = resources.getStringArray(R.array.types)
        var typeAdapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, typeArray)
        type.adapter = typeAdapter
        type.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                newLecture.type = type.selectedItem.toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }


        lectureRegister.setOnClickListener {
            if (lecture_name.text.toString() == "") {
                Toast.makeText(this, "분반명을 입력하세요.", Toast.LENGTH_LONG).show()
            } else if (classroom.text.toString() == "") {
                Toast.makeText(this, "강의실을 입력하세요", Toast.LENGTH_LONG).show()
            } else if(professorEdit.text.toString() == ""){
                Toast.makeText(this, "교수 이름을 입력하세요", Toast.LENGTH_LONG).show()
            }else {
                var subjectPushKey = intent.getStringExtra("subjectPushKey")
                var subjectPushReference = root.child("/$semester/subject/$subjectPushKey")

                var lecturePushKey = subjectPushReference.child("class").push().key
                subjectPushReference.child("class/$lecturePushKey").setValue(lecture_name.text.toString())
                var p = root.child( "/$semester/classInfo/$lecturePushKey")

                var time = p.child("time/0")
                var professor = p.child("professor")
                var title = p.child("title")
                var type = p.child("type")
                time.child("day").setValue(newLecture.day)
                time.child("start").setValue(newLecture.startHour+":"+newLecture.startMinute)
                time.child("end").setValue(newLecture.endHour+"$:"+newLecture.endMinute)
                time.child("place").setValue(classroom.text.toString())
                title.setValue(intent.getStringExtra("title"))
                professor.setValue(professorEdit.text.toString())
                type.setValue(newLecture.type)

                var intent = Intent(this, AddSubject::class.java)
                intent.putExtra("subjectPushKey", subjectPushKey)
                intent.putExtra("semester", semester)
                intent.putExtra("university", university)
                startActivity(intent)
            }
        }
    }

    data class NewLecture(
        var day: String,
        var startHour: String,
        var startMinute: String,
        var endHour: String,
        var endMinute: String,
        var type: String
    )
}