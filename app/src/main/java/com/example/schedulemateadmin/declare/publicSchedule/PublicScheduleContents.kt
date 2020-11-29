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
        declareTime.text = intent.getStringExtra("declareTime")
        var semester = intent.getStringExtra("semester")
        var classKey = intent.getStringExtra("classKey")
        var scheduleKey = intent.getStringExtra("scheduleKey")
        var scheduleTypeKey = intent.getStringExtra("scheduleTypeKey")
        var nickname = intent.getStringExtra("nickname")
        var registrant = intent.getStringExtra("registrant")

        var root = FirebaseDatabase.getInstance().reference
        //var scheduleRef = root.child("/$university/declare/schedule/$scheduleTypeKey")
        // scheduleTitle 넣기
        var scheduleTypeRef =
            root.child("/$university/$semester/classInfo/$classKey/schedule/$scheduleTypeKey")
        writerNickName.text = intent.getStringExtra("nickname")
        root.child("/user/$registrant/declared").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}
            override fun onDataChange(snapshot: DataSnapshot) {
                num.text = snapshot.getValue(String::class.java)
            }
        })
        scheduleTitle.text = scheduleTypeRef.key.toString()
        scheduleTypeRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}
            override fun onDataChange(snapshot: DataSnapshot) {
                scheduleTitle.text = snapshot.key.toString()
                if (snapshot.key.toString() == "homeWork") {
                    var contents = ""
                    var timeLimit = snapshot.child("/$scheduleKey/timeLimit").value.toString()
                    Log.e("timeLimit", timeLimit)

                    var content = snapshot.child("/$scheduleKey/content").value.toString()

                    contents = "과제: $content \n제출 기한: $timeLimit"
                    scheduleContent.text = contents
                } else {
                    var contents = ""
                    var startTime = snapshot.child("/$scheduleKey/startTime").value.toString()
                    var endTime = snapshot.child("/$scheduleKey/endTime").value.toString()
                    var place = snapshot.child("/$scheduleKey/place").value.toString()

                    contents = "장소: $place \n시험 시작 시간: $startTime \n시험 종료 시간: $endTime"
                    Log.e("contents", contents)
                    scheduleContent.text = contents

                }
            }
        })




        contents_delete.setOnClickListener {
            val popUp = AlertDialog.Builder(this)
            popUp.setTitle("해당 공유 일정을 삭제하시겠습니까?")
            popUp.setPositiveButton(
                "삭제",
                { dialog: DialogInterface?, which: Int ->
                    scheduleTypeRef.child(scheduleKey).child("place").removeValue()  // classInfo
                    root.child("/$university/declare/schedule/$scheduleTypeKey/$scheduleKey").child("classTitle").removeValue()
                    if(num.text.toString().toInt() < 10)
                        root.child("/user/$registrant/declared").setValue((num.text.toString().toInt() + 1).toString())
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
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
    }
}