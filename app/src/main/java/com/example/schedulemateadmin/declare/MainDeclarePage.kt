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
import com.example.schedulemateadmin.declare.community.CommunityList
import com.example.schedulemateadmin.declare.publicSchedule.PublicScheduleList
import com.example.schedulemateadmin.timeschedule.MainTimeSchedulePage
import kotlinx.android.synthetic.main.main_declare_page.*

class MainDeclarePage : AppCompatActivity(){
    lateinit var university:String
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var menuInflater : MenuInflater = getMenuInflater()
        menuInflater.inflate(R.menu.management, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.declare ->
                moveTo(Intent(this, MainDeclarePage::class.java))
            R.id.timeSchedule -> {
                moveTo(Intent(this, MainTimeSchedulePage::class.java))

            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_declare_page)

        university = intent.getStringExtra("university")

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val actionBar = getSupportActionBar()
        actionBar!!.setDisplayShowTitleEnabled(false)
        toolbarTitle.text = toolbarTitle.text.toString()+ " / $university"

        val comment = findViewById<TextView>(R.id.comment)
        val community = findViewById<TextView>(R.id.community)
        val public_calender = findViewById<TextView>(R.id.public_calender)

        comment.setOnClickListener {
            moveTo(Intent(this, CommentList::class.java))
        }
        community.setOnClickListener {
            moveTo(Intent(this, CommunityList::class.java))
        }
        public_calender.setOnClickListener {
            moveTo(Intent(this, PublicScheduleList::class.java))
        }
    }
    fun moveTo(intent:Intent){
        intent.putExtra("university", university)
        startActivity(intent)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
    }
}