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
import com.example.schedulemateadmin.declare.MainTimeSchedulePageRecyclerviewAdapter
import kotlinx.android.synthetic.main.main_timeschedule_page.*

class MainTimeSchedulePage : AppCompatActivity(){
    var timeSchedules = ArrayList<TimeScheduleData>()
    lateinit var university: String
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var menuInflater : MenuInflater = getMenuInflater()
        menuInflater.inflate(R.menu.management, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.timeSchedule ->
                moveTo(Intent(this, MainTimeSchedulePage::class.java))
            R.id.declare ->{
                val intent = Intent()
                intent.setClass(this,
                    com.example.schedulemateadmin.declare.MainDeclarePage::class.java)
                moveTo(intent)

            }
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_timeschedule_page)

        university = intent.getStringExtra("university")

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val actionBar = getSupportActionBar()
        actionBar!!.setDisplayShowTitleEnabled(false)
        toolbarTitle.text = "학기목록 / $university"
        val adapter =
            MainTimeSchedulePageRecyclerviewAdapter(
                this,
                timeSchedules,
                university
            )
        timeschedule_recyclerview.adapter = adapter
        timeschedule_recyclerview.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        val layoutManager = LinearLayoutManager(this)
        timeschedule_recyclerview.layoutManager = layoutManager

        registerSemester.setOnClickListener {
            moveTo(Intent(this, AddSemester::class.java))
        }
    }
    fun moveTo(intent:Intent){
        intent.putExtra("university", university)
        startActivity(intent)
    }
}
