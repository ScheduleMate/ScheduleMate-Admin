package com.example.schedulemateadmin.declare.comment

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.schedulemateadmin.R
import com.example.schedulemateadmin.SelectUniversity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.comment_recyclerview.view.*

class CommentRecyclerviewAdapter(
    val context: Context,
    val comments: ArrayList<CommentData>,
    val university: String
) :
    RecyclerView.Adapter<CommentRecyclerviewAdapter.ViewHolder>() {
    init {
        var root = FirebaseDatabase.getInstance().reference
        var commentPath = root.child("$university/declare/comment")
        commentPath.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}
            override fun onDataChange(snapshot: DataSnapshot) { //snapshot = comment
                if (comments.size != null)
                    comments.clear()
                for (commentKey in snapshot.children) {
                    var commentKeyData = commentKey.key.toString()
                    var nickname = commentKey.child("writerNickName").value.toString()
                    var time = commentKey.child("time").value.toString()
                    var title = commentKey.child("title").value.toString()
                    var lecture = commentKey.child("class").value.toString()
                    var lectureKey = commentKey.child("classKey").value.toString()
                    var communityKey = commentKey.child("communityKey").value.toString()
                    var reason = commentKey.child("reason").value.toString()
                    comments.add(
                        CommentData(
                            nickname,
                            "$title($lecture)",
                            time,
                            reason,
                            commentKeyData,
                            lectureKey,
                            communityKey,
                            title
                        )
                    )
                    notifyDataSetChanged()
                }
            }
        })
    }

    override fun getItemCount(): Int = comments.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = comments[position]
        val listener = View.OnClickListener {
            var intent = Intent(context, CommentContents::class.java)
            intent.putExtra("nickname", item.nickname)
            intent.putExtra("lecture", item.lecture)
            intent.putExtra("title", item.title)
            intent.putExtra("time", item.time)
            intent.putExtra("lectureKeyData", item.lectureKey)
            intent.putExtra("communityKeyData", item.communityKey)
            intent.putExtra("declareReason", item.reason)
            intent.putExtra("commentKeyData", item.commentKey)
            intent.putExtra("university", university)
            context.startActivity(intent)
        }
        holder.apply {
            bind(listener, item)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.comment_recyclerview, parent, false)

        return ViewHolder(view)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var view: View = view

        fun bind(listener: View.OnClickListener, item: CommentData) {
            view.nickname.text = item.nickname
            view.lecture.text = item.lecture
            view.declareTime.text = item.time

            view.nickname.setOnClickListener(listener)
            view.lecture.setOnClickListener(listener)
            view.declareTime.setOnClickListener(listener)
        }
    }
}