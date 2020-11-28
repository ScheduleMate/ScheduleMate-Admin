package com.example.schedulemateadmin.declare.comment

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.schedulemateadmin.R
import com.example.schedulemateadmin.declare.MainDeclarePage
import com.example.schedulemateadmin.timeschedule.MainTimeSchedulePage
import kotlinx.android.synthetic.main.comment_management.*

class CommentList :AppCompatActivity(){
    lateinit var university : String
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var menuInflater : MenuInflater = getMenuInflater()
        menuInflater.inflate(R.menu.management, menu)

        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.declare ->
                moveTo(Intent(this, MainDeclarePage::class.java))
            R.id.timeSchedule ->{
                moveTo(Intent(this, MainTimeSchedulePage::class.java))

            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.comment_management)

        university = intent.getStringExtra("university")

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val actionBar = getSupportActionBar()
        actionBar!!.setDisplayShowTitleEnabled(false)
        val toolbarTitle = findViewById<TextView>(R.id.toolbarTitle)
        toolbarTitle.text = "댓글 관리 / $university"

        var comments = ArrayList<CommentData>()
        val adapter =
            CommentRecyclerviewAdapter(
                this,
                comments,
                university
            )
        comment_recyclerview.adapter = adapter

        val layoutManager = LinearLayoutManager(this)
        comment_recyclerview.layoutManager = layoutManager
    }
    fun moveTo(intent: Intent){
        intent.putExtra("university", university)
        startActivity(intent)
    }
}
