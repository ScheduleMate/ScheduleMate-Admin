package com.example.schedulemateadmin.declare.publicCalendar

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.schedulemateadmin.R
import kotlinx.android.synthetic.main.public_calendar_recyclerview.view.*

class PublicCalendarRecyclerviewAdapter(val context:Context, val pubCalanders: ArrayList<PublicCalenderData>) :
    RecyclerView.Adapter<PublicCalendarRecyclerviewAdapter.ViewHolder>() {

    init{

    }

    override fun getItemCount(): Int = pubCalanders.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = pubCalanders[position]
        val listener = View.OnClickListener {
            context.startActivity(Intent(context, PublicCalendarContents::class.java))
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

        fun bind(listener: View.OnClickListener, item: PublicCalenderData) {
            view.nickname.text = item.nickname
            view.accumulate_num.text = item.accumulate_num
            view.lecture.text = item.lecture

            view.nickname.setOnClickListener(listener)
            view.accumulate_num.setOnClickListener(listener)
            view.lecture.setOnClickListener(listener)
        }
    }
}