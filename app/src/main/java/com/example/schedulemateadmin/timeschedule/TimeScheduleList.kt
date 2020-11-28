package com.example.schedulemateadmin.timeschedule

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.schedulemateadmin.R
import com.example.schedulemateadmin.declare.TimeScheduleListRecyclerviewAdapter
import kotlinx.android.synthetic.main.timeschedule_list.*

class TimeScheduleList : AppCompatActivity(){
    var subjects = ArrayList<TimeScheduleListData>()
    var semester = ""
    lateinit var university: String
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var menuInflater : MenuInflater = getMenuInflater()
        menuInflater.inflate(R.menu.timeschedule_management, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.add_subject-> // 과목 추가
                moveTo(Intent(this, AddSubject::class.java))
            R.id.add_belong-> // 소속 추가
                moveTo(Intent(this, AddBelong::class.java))
            R.id.add_division-> // 구분 추가
                moveTo(Intent(this, AddDivision::class.java))
            android.R.id.home->{
                moveTo(Intent(this, MainTimeSchedulePage::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.timeschedule_list)
        semester = intent.getStringExtra("semester")
        university = intent.getStringExtra("university")

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbarTitle.text = semester
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbarTitle.text = "$semester / $university"

        val actionBar = getSupportActionBar()
        actionBar!!.setDisplayShowTitleEnabled(false)

        timeschedule_list_recyclerview.adapter = TimeScheduleListRecyclerviewAdapter(this, subjects, semester, university)

        val layoutManager = LinearLayoutManager(this)
        timeschedule_list_recyclerview.layoutManager = layoutManager
    }

    fun moveTo(intent: Intent){
        intent.putExtra("semester", semester)
        intent.putExtra("university", university)
        startActivity(intent)
    }
}