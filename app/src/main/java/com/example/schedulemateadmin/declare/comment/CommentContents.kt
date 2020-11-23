package com.example.schedulemateadmin.declare.comment

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.schedulemateadmin.R
import com.example.schedulemateadmin.declare.MainDeclarePage
import kotlinx.android.synthetic.main.comment_contents.*

class CommentContents : AppCompatActivity(){
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
        setContentView(R.layout.comment_contents)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val actionBar = getSupportActionBar()
        actionBar!!.setDisplayShowTitleEnabled(false)

        contents_delete.setOnClickListener {
            val popUp = AlertDialog.Builder(this)
            popUp.setTitle("해당 댓글을 비공개처리하시겠습니까?")
            popUp.setPositiveButton(
                "비공개",
                {dialog: DialogInterface?, which: Int ->

                }
            )
            popUp.setNegativeButton(
                "취소",
                {dialog: DialogInterface?, which: Int ->

                }
            )
            popUp.setCancelable(false)
            popUp.show()
        }
    }
}