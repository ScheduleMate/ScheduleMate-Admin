package com.example.schedulemateadmin.declare

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.schedulemateadmin.R
import com.example.schedulemateadmin.timeschedule.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.timeschedule_list_recyclerview.view.*
import kotlinx.android.synthetic.main.timeschedule_recyclerview.view.*

class TimeScheduleListRecyclerviewAdapter(
    val context: Context,
    val subjects: ArrayList<TimeScheduleListData>,
    val semester: String,
    val university:String
) :
    RecyclerView.Adapter<TimeScheduleListRecyclerviewAdapter.ViewHolder>() {

    var root = FirebaseDatabase.getInstance().reference.child(university)

    init {
        val subjectRoot = root.child(semester).child("subject")
        subjectRoot.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(subjects.size != 0){
                    subjects.clear()
                }
                for (subject in snapshot.children) { // subject고유키
                    val dependency = subject.child("dependency").value.toString()
                    val title = subject.child("title").value.toString()
                    val subjectPushKey = subject.key.toString()
                    subjects.add(TimeScheduleListData(dependency, title, subjectPushKey))
                }
                notifyDataSetChanged()

            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    override fun getItemCount(): Int = subjects.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = subjects[position]
        val listener = View.OnClickListener {
            val intent = Intent(context, AddSubject::class.java)
            intent.putExtra("subjectPushKey", item.subjectPushKey)
            intent.putExtra("semester", semester)
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
            .inflate(R.layout.timeschedule_list_recyclerview, parent, false)

        return ViewHolder(view)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var view: View = view

        fun bind(listener: View.OnClickListener, item: TimeScheduleListData) {
            view.belong.text = item.belong
            view.subjectName.text = item.subjectName

            view.belong.setOnClickListener(listener)
            view.subjectName.setOnClickListener(listener)
        }
    }
}