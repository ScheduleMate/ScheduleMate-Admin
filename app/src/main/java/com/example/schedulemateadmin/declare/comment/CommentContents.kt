package com.example.schedulemateadmin.declare.comment

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
import com.example.schedulemateadmin.declare.community.CommunityList
import com.example.schedulemateadmin.timeschedule.MainTimeSchedulePage
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.comment_contents.*
import kotlinx.android.synthetic.main.comment_contents.contents_delete
import kotlinx.android.synthetic.main.comment_contents.declareTime
import kotlinx.android.synthetic.main.comment_contents.declare_reason
import kotlinx.android.synthetic.main.comment_contents.lecture
import kotlinx.android.synthetic.main.comment_contents.nickname
import kotlinx.android.synthetic.main.main_declare_page.*

class CommentContents : AppCompatActivity() {
    private lateinit var root: DatabaseReference
    private lateinit var university: String
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
        setContentView(R.layout.comment_contents)

        university = intent.getStringExtra("university")

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val actionBar = getSupportActionBar()
        actionBar!!.setDisplayShowTitleEnabled(false)
        var toolbarTitle = findViewById<TextView>(R.id.toolbarTitle)
        toolbarTitle.text = "댓글 관리 / $university"

        nickname.text = intent.getStringExtra("nickname")
        lecture.text = intent.getStringExtra("lecture")
        declareTime.text = intent.getStringExtra("time")
        declare_reason.text = intent.getStringExtra("declareReason")

        root = FirebaseDatabase.getInstance().reference
        var commentKeyData = intent.getStringExtra("commentKeyData")
        var communityKeyData = intent.getStringExtra("communityKeyData")
        var lectureKeyData = intent.getStringExtra("lectureKeyData")
        var comment = root.child("community").child(lectureKeyData).child("/post/" + communityKeyData)
            .child("/comment/" + commentKeyData)
        comment.child("content").addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onCancelled(error: DatabaseError) {}
            override fun onDataChange(snapshot: DataSnapshot) {
                comment_contents.text = snapshot.getValue(String::class.java)
            }
        })


        contents_delete.setOnClickListener {
            val popUp = AlertDialog.Builder(this)
            popUp.setTitle("해당 댓글을 삭제하시겠습니까?")
            popUp.setPositiveButton(
                "삭제",
                { dialog: DialogInterface?, which: Int ->
                    root.child("/declare/comment/" + commentKeyData).removeValue()
                    comment.removeValue()
                    moveTo(Intent(this, CommentList::class.java))
                    Toast.makeText(applicationContext, "삭제가 완료되었습니다.", Toast.LENGTH_LONG).show()
                }
            )
            popUp.setNegativeButton(
                "취소",
                { dialog: DialogInterface?, which: Int -> }
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