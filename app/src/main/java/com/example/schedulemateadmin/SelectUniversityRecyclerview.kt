package com.example.schedulemateadmin.declare

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.schedulemateadmin.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.select_university_recyclerview.view.*

class SelectUniversityRecyclerview(
    val context: Context

) :
    RecyclerView.Adapter<SelectUniversityRecyclerview.ViewHolder>() {
    val universities = ArrayList<University>()
    var root = FirebaseDatabase.getInstance().reference

    init {
        root.child("/info/university").addValueEventListener(object :ValueEventListener{
            override fun onCancelled(error: DatabaseError) {}
            override fun onDataChange(snapshot: DataSnapshot) {
                for(university in snapshot.children)
                    universities.add(University(university.value.toString()))
                notifyDataSetChanged()
            }
        })
    }

    override fun getItemCount(): Int = universities.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = universities[position]
        val listener = View.OnClickListener {
            var intent = Intent(context, MainDeclarePage::class.java)
            intent.putExtra("university", item.university)
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
            .inflate(R.layout.select_university_recyclerview, parent, false)
        return ViewHolder(view)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var view: View = view

        fun bind(listener: View.OnClickListener, item: University) {
            view.university.text = item.university
            view.university.setOnClickListener(listener)
        }
    }
    data class University(val university:String)
}