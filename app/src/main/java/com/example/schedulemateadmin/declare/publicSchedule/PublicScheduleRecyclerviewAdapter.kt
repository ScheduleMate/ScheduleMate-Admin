package com.example.schedulemateadmin.declare.publicSchedule

import android.content.Context
import android.content.Intent
import android.util.Log
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
    val university: String
) :
    RecyclerView.Adapter<PublicScheduleRecyclerviewAdapter.ViewHolder>() {
    var root = FirebaseDatabase.getInstance().reference.child("/$university/declare/schedule")
    val pSchedules = ArrayList<PublicScheduleData>()
    var sData = PublicScheduleData("", "", "", "", "", "", "", "")

    init {
        root.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {}
            override fun onDataChange(snapshot: DataSnapshot) {
                if (pSchedules.size != 0)
                    pSchedules.clear()
                for (scheduleType in snapshot.children) {
                    var scheduleTypeKey = scheduleType.key.toString()// final/mid/homework
                    for (schedule in scheduleType.children) { // 날짜
                        var scheduleKey = schedule.key.toString()
                        var classTitle = schedule.child("classTitle").value.toString()
                        var classKey = schedule.child("classKey").value.toString()
                        var declareTime = schedule.child("declareTime").value.toString()
                        var semester = schedule.child("semester").value.toString()
                        var registrant = schedule.child("registrant").value.toString()
                        sData.nickname
                        FirebaseDatabase.getInstance().reference.child("/user/$registrant/nickName")
                            .addValueEventListener(object : ValueEventListener {
                                override fun onCancelled(error: DatabaseError) {}
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    sData.nickname = snapshot.getValue(String::class.java)!!
                                    pSchedules.add(
                                        PublicScheduleData(
                                            sData.nickname,
                                            "$classTitle",
                                            declareTime,
                                            semester,
                                            registrant,
                                            classKey,
                                            scheduleKey,
                                            scheduleTypeKey
                                        )
                                    )
                                    notifyDataSetChanged()
                                }
                            })

                    }
                }

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
            intent.putExtra("declareTime", item.declareTime)
            intent.putExtra("semester", item.semester)
            intent.putExtra("registrant", item.registrant)
            intent.putExtra("classKey", item.classKey)
            intent.putExtra("scheduleKey", item.scheduleKey)
            intent.putExtra("scheduleTypeKey", item.scheduleTypeKey)
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
            view.time.text = item.declareTime

            view.nickname.setOnClickListener(listener)
            view.time.setOnClickListener(listener)
            view.lecture.setOnClickListener(listener)
        }
    }

    data class PublicScheduleData(
        var nickname: String,
        val lecture: String,
        val declareTime: String,
        val semester: String,
        val registrant: String,
        val classKey: String,
        val scheduleKey: String,
        val scheduleTypeKey: String
    )
}