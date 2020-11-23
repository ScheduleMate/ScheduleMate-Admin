package com.example.schedulemateadmin.declare

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.schedulemateadmin.R
import com.example.schedulemateadmin.declare.comment.CommentList
import com.example.schedulemateadmin.declare.community.CommnunityList
import com.example.schedulemateadmin.declare.publicCalendar.PublicCalenderList

class MainDeclarePage : AppCompatActivity(){
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var menuInflater : MenuInflater = getMenuInflater()
        menuInflater.inflate(R.menu.management, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.declare ->
                startActivity(Intent(this, MainDeclarePage::class.java))
            R.id.timeSchedule -> {
                val intent = Intent()
                intent.setClass(
                    this,
                    com.example.schedulemateadmin.timeschedule.MainTimeSchedulePage::class.java
                )
                startActivity(intent)

            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_declare_page)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val actionBar = getSupportActionBar()
        actionBar!!.setDisplayShowTitleEnabled(false)

        val comment = findViewById<TextView>(R.id.comment)
        val community = findViewById<TextView>(R.id.community)
        val public_calender = findViewById<TextView>(R.id.public_calender)

        comment.setOnClickListener {
            startActivity(Intent(this, CommentList::class.java))
        }
        community.setOnClickListener {
            startActivity(Intent(this, CommnunityList::class.java))
        }
        public_calender.setOnClickListener {
            startActivity(Intent(this, PublicCalenderList::class.java))
        }
    }
}