package com.example.schedulemateadmin.declare.publicCalendar

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.schedulemateadmin.R
import com.example.schedulemateadmin.declare.MainDeclarePage
import kotlinx.android.synthetic.main.public_calender_management.*

class PublicCalenderList :AppCompatActivity(){
    var publicCalendars = ArrayList<PublicCalenderData>()
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
        setContentView(R.layout.public_calender_management)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val actionBar = getSupportActionBar()
        actionBar!!.setDisplayShowTitleEnabled(false)

        val adapter =
            PublicCalendarRecyclerviewAdapter(
                this,
                publicCalendars
            )
        public_calendar_recyclerview.adapter = adapter
        adapter.notifyDataSetChanged()

        val layoutManager = LinearLayoutManager(this)
        public_calendar_recyclerview.layoutManager = layoutManager
    }
}