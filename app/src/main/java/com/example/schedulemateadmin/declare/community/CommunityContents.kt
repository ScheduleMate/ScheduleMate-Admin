package com.example.schedulemateadmin.declare.community

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.schedulemateadmin.R
import com.example.schedulemateadmin.declare.MainDeclarePage
import com.example.schedulemateadmin.timeschedule.MainTimeSchedulePage
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.community_contents.*
import kotlinx.android.synthetic.main.community_contents.contents_delete
import kotlinx.android.synthetic.main.community_contents.declare_reason
import kotlinx.android.synthetic.main.community_contents.lecture
import kotlinx.android.synthetic.main.community_contents.nickname

class CommunityContents : AppCompatActivity(){
    private lateinit var communityRoot : DatabaseReference
    private lateinit var root : DatabaseReference
    private lateinit var university : String
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var menuInflater : MenuInflater = getMenuInflater()
        menuInflater.inflate(R.menu.management, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
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
        setContentView(R.layout.community_contents)

        university = intent.getStringExtra("university")

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val actionBar = getSupportActionBar()
        actionBar!!.setDisplayShowTitleEnabled(false)
        var toolbarTitle = findViewById<TextView>(R.id.toolbarTitle)
        toolbarTitle.text = "게시물 관리 / $university"

        nickname.text = intent.getStringExtra("nickname")
        lecture.text = intent.getStringExtra("lecture")
        declareTime.text = intent.getStringExtra("time")

        root = FirebaseDatabase.getInstance().reference
        communityRoot = root.child("community")
        val lecture = communityRoot.child(intent.getStringExtra("lectureKeyData"))
        val community = lecture.child("post").child(intent.getStringExtra("communityKeyData"))
        val declareReason = intent.getStringExtra("declareReason")
        val contentPath = community.child("content")
        Log.e("content", intent.getStringExtra("communityKeyData"))


        declare_reason.text = declareReason
        contentPath.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onCancelled(error: DatabaseError) {}
            override fun onDataChange(snapshot: DataSnapshot) {
                community_contents.text = snapshot.getValue(String::class.java)
            }
        })

        contents_delete.setOnClickListener {
            val popUp = AlertDialog.Builder(this)
            popUp.setTitle("해당 게시물을 삭제하시겠습니까?")
            popUp.setPositiveButton(
                "삭제",
                { dialog: DialogInterface?, which: Int ->
                    community.removeValue()
                    root.child("/declare/community/").child(lecture.key.toString()).child(intent.getStringExtra("communityKeyData")).removeValue()
                    moveTo(Intent(this, CommunityList::class.java))
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
    fun moveTo(intent: Intent){
        intent.putExtra("university", university)
        startActivity(intent)
    }
}