package com.example.schedulemateadmin.timeschedule

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.schedulemateadmin.R
import kotlinx.android.synthetic.main.add_subject.*

class AddSubject :AppCompatActivity(){

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home-> onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_subject)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val actionBar = getSupportActionBar()
        actionBar!!.setDisplayShowTitleEnabled(false)

        addLecture.setOnClickListener {
            startActivity(Intent(this, AddLecture::class.java))
        }

        var creditsArray = resources.getStringArray(R.array.credits)
        var creditsAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, creditsArray)
        credits.adapter = creditsAdapter

        var gradesArray = resources.getStringArray(R.array.grades)
        var gradesAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, gradesArray)
        grades.adapter = gradesAdapter

    }
}