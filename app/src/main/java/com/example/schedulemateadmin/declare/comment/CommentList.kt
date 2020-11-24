package com.example.schedulemateadmin.declare.comment

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
import kotlinx.android.synthetic.main.comment_management.*

class CommentList :AppCompatActivity(){
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var menuInflater : MenuInflater = getMenuInflater()
        menuInflater.inflate(R.menu.management, menu)

        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.declare ->
                startActivity(Intent(this, MainDeclarePage::class.java))
            R.id.timeSchedule ->{
                val intent = Intent()
                intent.setClass(this,
                    com.example.schedulemateadmin.timeschedule.MainTimeSchedulePage::class.java)
                startActivity(intent)

            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.comment_management)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val actionBar = getSupportActionBar()
        actionBar!!.setDisplayShowTitleEnabled(false)


        var comments = ArrayList<CommentData>()
        comments.add(
            CommentData(
                "bubble1010",
                "모바일 시스템(7)",
                "1",
                "0"
            )
        )

        comments.add(
            CommentData(
                "gildonghong",
                "클라우드 컴퓨팅(a)",
                "0",
                "0"
            )
        )

        comments.add(
            CommentData(
                "chulsu",
                "클라우드 컴퓨팅(a)",
                "1",
                "1"
            )
        )
        val adapter =
            CommentRecyclerviewAdapter(
                this,
                comments
            )
        comment_recyclerview.adapter = adapter
        adapter.notifyDataSetChanged()

        val layoutManager = LinearLayoutManager(this)
        comment_recyclerview.layoutManager = layoutManager
    }
}
