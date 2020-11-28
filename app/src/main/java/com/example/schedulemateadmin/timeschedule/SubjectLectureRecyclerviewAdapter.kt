package com.example.schedulemateadmin.declare

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.schedulemateadmin.R
import com.example.schedulemateadmin.timeschedule.AddSubject
import com.example.schedulemateadmin.timeschedule.SubjectLectureData
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.add_lecture_recyclerview.view.*

class SubjectLectureRecyclerviewAdapter(
    val context: Context,
    val lectures: ArrayList<SubjectLectureData>,
    subjectPushReference :DatabaseReference?,
    classInfoReference  :DatabaseReference?,
    val university: String
) :
    RecyclerView.Adapter<SubjectLectureRecyclerviewAdapter.ViewHolder>() {
    lateinit var classes : Iterable<DataSnapshot>

    init {
        if (subjectPushReference != null) {
            subjectPushReference!!.child("class")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        classes = snapshot.children  // 분반 고유키
                        notifyDataSetChanged()
                    }
                    override fun onCancelled(error: DatabaseError) {}
                })
            classInfoReference!!.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(classInfo: DataSnapshot) {
                    if(lectures.size != 0)
                        lectures.clear()
                    for(c in classes){
                        Log.e("classes", c.key.toString())

                        var classRoot = classInfo.child(c.key.toString())
                        var timeReference = classRoot.child("/time/0")
                        var day = ""; var end = ""; var place = ""; var start = ""
                        for(t in timeReference.children){
                            when(t.key.toString()){
                                "day"-> day = t.value.toString()
                                "end"-> end = t.value.toString()
                                "place"-> place = t.value.toString()
                                "start"-> start = t.value.toString()
                            }
                        }
                        var time = day + start + "~" + end
                        var type = classRoot.child("type").value.toString()
                        var professor = classRoot.child("professor").value.toString()
                        var className = c.value.toString() + "($type)"
                        var classRoom = timeReference.child("place").value.toString()
                        lectures.add(SubjectLectureData(className, time, classRoom, professor, c.key.toString()))
                    }
                    notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {}
            })
        }
    }

    override fun getItemCount(): Int = lectures.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = lectures[position]
        var listener = View.OnClickListener{

        }
        holder.apply {
            bind(item)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.add_lecture_recyclerview, parent, false)

        return ViewHolder(view)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var view: View = view

        fun bind(item: SubjectLectureData) {
            view.lecture_name.text = item.lecture_name
            view.lecture_time.text = item.lecture_time
            view.classroom.text = item.classroom
            view.professor.text = item.professor
        }
    }
}