package com.example.schedulemateadmin.declare

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.schedulemateadmin.R
import com.example.schedulemateadmin.timeschedule.TimeScheduleData
import com.example.schedulemateadmin.timeschedule.TimeScheduleList
import kotlinx.android.synthetic.main.timeschedule_recyclerview.view.*

class TimeScheduleRecyclerviewAdapter(val context:Context, val semesters: ArrayList<TimeScheduleData>) :
    RecyclerView.Adapter<TimeScheduleRecyclerviewAdapter.ViewHolder>() {

    override fun getItemCount(): Int = semesters.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = semesters[position]
        val listener = View.OnClickListener {
            context.startActivity(Intent(context, TimeScheduleList::class.java))
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

        fun bind(listener:View.OnClickListener, item: TimeScheduleData) {
            view.semester.text = item.semester
            view.semester.setOnClickListener(listener)
        }
    }
}