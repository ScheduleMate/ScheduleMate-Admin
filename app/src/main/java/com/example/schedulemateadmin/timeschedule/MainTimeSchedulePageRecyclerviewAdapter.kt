package com.example.schedulemateadmin.declare

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.schedulemateadmin.R
import com.example.schedulemateadmin.timeschedule.TimeScheduleData
import com.example.schedulemateadmin.timeschedule.TimeScheduleList
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.timeschedule_recyclerview.view.*

class MainTimeSchedulePageRecyclerviewAdapter(
    val context: Context,
    val semesters: ArrayList<TimeScheduleData>,
    val university:String
) :
    RecyclerView.Adapter<MainTimeSchedulePageRecyclerviewAdapter.ViewHolder>() {

    var dbRoot = FirebaseDatabase.getInstance().reference

    init {
        dbRoot.child(university).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (semesters.size != 0) {
                    semesters.clear()
                }
                for (semester in snapshot.children) {
                    if (semester.key.toString()!="declare" && semester.key.toString() != "info" && semester.key.toString() != "timetable")
                        semesters.add(TimeScheduleData(semester.key.toString()))
                }
                notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    override fun getItemCount(): Int = semesters.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = semesters[position]
        val listener = View.OnClickListener {
            val intent = Intent(context, TimeScheduleList::class.java)
            intent.putExtra("semester", item.semester)
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
            .inflate(R.layout.timeschedule_recyclerview, parent, false)

        return ViewHolder(view)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var view: View = view

        fun bind(listener: View.OnClickListener, item: TimeScheduleData) {
            view.semester.text = item.semester
            view.semester.setOnClickListener(listener)
        }
    }
}