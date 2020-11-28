package com.example.schedulemateadmin.declare.community

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
import kotlinx.android.synthetic.main.community_recyclerview.view.*

class CommunityRecyclerviewAdapter(
    val context: Context,
    val communities: ArrayList<CommunityData>,
    val university: String
) :
    RecyclerView.Adapter<CommunityRecyclerviewAdapter.ViewHolder>() {
    init {
        val root = FirebaseDatabase.getInstance().reference
        val communityPath = root.child("$university/declare/community")

        communityPath.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (communities.size != 0)
                    communities.clear()
                for (lectureKey in snapshot.children) { // 분반 고유키1,2...
                    for (community in lectureKey.children) { // community 고유키
                        var nickname = community.child("writerNickName").value.toString()
                        var className = community.child("class").value.toString()
                        var time = community.child("time").value.toString()
                        var title = community.child("title").value.toString()
                        var reason = community.child("reason").value.toString()
                        var lectureKeyData = lectureKey.key.toString()
                        var communityKeyData = community.key.toString()
                        communities.add(
                            CommunityData(
                                nickname,
                                "$title($className)",
                                time,
                                title,
                                lectureKeyData,
                                communityKeyData,
                                reason
                            )
                        )

                    }
                }
                notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    override fun getItemCount(): Int = communities.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = communities[position]
        val listener = View.OnClickListener {
            var intent = Intent(context, CommunityContents::class.java)
            intent.putExtra("nickname", item.nickname)
            intent.putExtra("lecture", item.lecture)
            intent.putExtra("title", item.title)
            intent.putExtra("time", item.time)
            intent.putExtra("lectureKeyData", item.lectureKey)
            intent.putExtra("communityKeyData", item.communityKey)
            intent.putExtra("declareReason", item.reason)
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
            .inflate(R.layout.community_recyclerview, parent, false)

        return ViewHolder(view)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var view: View = view

        fun bind(listener: View.OnClickListener, item: CommunityData) {
            view.nickname.text = item.nickname
            view.lecture.text = item.lecture
            view.time.text = item.time

            view.nickname.setOnClickListener(listener)
            view.lecture.setOnClickListener(listener)
            view.time.setOnClickListener(listener)
        }
    }
}