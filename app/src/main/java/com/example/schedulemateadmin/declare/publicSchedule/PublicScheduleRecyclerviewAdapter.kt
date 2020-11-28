package com.example.schedulemateadmin.declare.publicSchedule

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.schedulemateadmin.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.public_calendar_recyclerview.view.*

class PublicScheduleRecyclerviewAdapter(
    val context: Context,
    val pSchedules: ArrayList<PublicScheduleData>,
    val university: String
) :
    RecyclerView.Adapter<PublicScheduleRecyclerviewAdapter.ViewHolder>() {
    var root = FirebaseDatabase.getInstance().reference.child("/$university/declare/schedule")

    init {
        root.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}
            override fun onDataChange(snapshot: DataSnapshot) {
                for (schedule in snapshot.children) {
                    var scheduleKey = schedule.key.toString()
                    var lecture = schedule.child("class").value.toString()
                    var classTitle = schedule.child("classTitle").value.toString()
                    var lectureKey = schedule.child("classKey").value.toString()
                    var time = schedule.child("time").value.toString()
                    var writerNickName = schedule.child("writerNickName").value.toString()
                    var semester = schedule.child("semester").value.toString()
                    var writer = schedule.child("writer").value.toString()

                    pSchedules.add(
                        PublicScheduleData(
                            writerNickName,
                            "$classTitle($lecture)",
                            time,
                            scheduleKey,
                            lectureKey,
                            semester,
                            writer
                        )
                    )
                }
                notifyDataSetChanged()
            }
        })
    }

    override fun getItemCount(): Int = pSchedules.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = pSchedules[position]
        val listener = View.OnClickListener {
            var intent = Intent(context, PublicScheduleContents::class.java)
            intent.putExtra("university", university)
            intent.putExtra("nickname", item.nickname)
            intent.putExtra("lecture", item.lecture)
            intent.putExtra("lectureKey", item.lectureKey)
            intent.putExtra("scheduleKey", item.scheduleKey)
            intent.putExtra("time", item.time)
            intent.putExtra("semester", item.semester)
            intent.putExtra("writer", item.writer)
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
            .inflate(R.layout.public_calendar_recyclerview, parent, false)
        return ViewHolder(view)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var view: View = view

        fun bind(listener: View.OnClickListener, item: PublicScheduleData) {
            view.nickname.text = item.nickname
            view.lecture.text = item.lecture
            view.time.text = item.time

            view.nickname.setOnClickListener(listener)
            view.time.setOnClickListener(listener)
            view.lecture.setOnClickListener(listener)
        }
    }
}