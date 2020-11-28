package com.example.schedulemateadmin.declare.publicSchedule

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.schedulemateadmin.R
import com.example.schedulemateadmin.declare.MainDeclarePage
import com.example.schedulemateadmin.declare.comment.CommentList
import com.example.schedulemateadmin.timeschedule.MainTimeSchedulePage
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.public_calendar_contents.*

class PublicScheduleContents : AppCompatActivity() {
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
        setContentView(R.layout.public_calendar_contents)

        university = intent.getStringExtra("university")

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val actionBar = getSupportActionBar()
        actionBar!!.setDisplayShowTitleEnabled(false)

        toolbarTitle.text = "공유 일정 관리 / $university"
        nickname.text = intent.getStringExtra("nickname")
        lecture.text = intent.getStringExtra("lecture")
        declareTime.text = intent.getStringExtra("time")

        var semester = intent.getStringExtra("semester")
        var lectureKey = intent.getStringExtra("lectureKey")
        var scheduleKey = intent.getStringExtra("scheduleKey")
        var root = FirebaseDatabase.getInstance().reference
        var classInfo = root.child("/$university/$semester/classInfo")
        var scheduleRef = classInfo.child("/$lectureKey/schedule/$scheduleKey")
        var writer = intent.getStringExtra("writer")

        writerNickName.text = intent.getStringExtra("nickname")
        scheduleRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}
            override fun onDataChange(snapshot: DataSnapshot) {
                scheduleTitle.text = snapshot.child("title").value.toString()
                scheduleContent.text = snapshot.child("content").value.toString()
            }
        })
        root.child("/user/$writer/declared").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}
            override fun onDataChange(snapshot: DataSnapshot) {
                num.text = snapshot.getValue(String::class.java)
            }
        })


        contents_delete.setOnClickListener {
            val popUp = AlertDialog.Builder(this)
            popUp.setTitle("해당 공유 일정을 삭제하시겠습니까?")
            popUp.setPositiveButton(
                "삭제",
                { dialog: DialogInterface?, which: Int ->
                    scheduleRef.removeValue()
                    root.child("/$university/declare/schedule/$scheduleKey").removeValue()
                    moveTo(Intent(this, PublicScheduleList::class.java))
                    Toast.makeText(applicationContext, "삭제가 완료되었습니다.", Toast.LENGTH_LONG).show()
                }
            )
            popUp.setNegativeButton(
                "취소",
                { dialog: DialogInterface?, which: Int ->

                }
            )
            popUp.setCancelable(false)
            popUp.show()
        }
    }

    fun moveTo(intent: Intent) {
        intent.putExtra("university", university)
        startActivity(intent)
    }
}