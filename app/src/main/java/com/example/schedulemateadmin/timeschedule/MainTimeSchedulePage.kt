package com.example.schedulemateadmin.timeschedule

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.schedulemateadmin.R
import com.example.schedulemateadmin.declare.TimeScheduleRecyclerviewAdapter
import kotlinx.android.synthetic.main.main_timeschedule_page.*

class MainTimeSchedulePage : AppCompatActivity(){
    var timeSchedules = ArrayList<TimeScheduleData>()
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var menuInflater : MenuInflater = getMenuInflater()
        menuInflater.inflate(R.menu.management, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.timeSchedule ->
                startActivity(Intent(this, MainTimeSchedulePage::class.java))
            R.id.declare ->{
                val intent = Intent()
                intent.setClass(this,
                    com.example.schedulemateadmin.declare.MainDeclarePage::class.java)
                startActivity(intent)

            }
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_timeschedule_page)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val actionBar = getSupportActionBar()
        actionBar!!.setDisplayShowTitleEnabled(false)

        timeSchedules.add(TimeScheduleData("2020년 2학기"))
        timeSchedules.add(TimeScheduleData("2020년 1학기"))

        val adapter =
            TimeScheduleRecyclerviewAdapter(
                this,
                timeSchedules
            )
        timeschedule_recyclerview.adapter = adapter
        adapter.notifyDataSetChanged()

        val layoutManager = LinearLayoutManager(this)
        timeschedule_recyclerview.layoutManager = layoutManager
    }
}