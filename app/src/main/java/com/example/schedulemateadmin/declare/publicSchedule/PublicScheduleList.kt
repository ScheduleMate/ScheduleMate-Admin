package com.example.schedulemateadmin.declare.publicSchedule

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
import com.example.schedulemateadmin.timeschedule.MainTimeSchedulePage
import kotlinx.android.synthetic.main.public_calender_management.*

class PublicScheduleList : AppCompatActivity() {
    var publicCalendars = ArrayList<PublicScheduleData>()
    lateinit var university: String
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var menuInflater: MenuInflater = getMenuInflater()
        menuInflater.inflate(R.menu.management, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
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
        setContentView(R.layout.public_calender_management)

        university = intent.getStringExtra("university")

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val actionBar = getSupportActionBar()
        actionBar!!.setDisplayShowTitleEnabled(false)

        val adapter =
            PublicScheduleRecyclerviewAdapter(this, publicCalendars, university)
        public_calendar_recyclerview.adapter = adapter
        adapter.notifyDataSetChanged()

        val layoutManager = LinearLayoutManager(this)
        public_calendar_recyclerview.layoutManager = layoutManager
    }

    fun moveTo(intent: Intent) {
        intent.putExtra("university", university)
        startActivity(intent)
    }
}