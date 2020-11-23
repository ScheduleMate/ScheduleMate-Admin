package com.example.schedulemateadmin.declare.community

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.schedulemateadmin.R
import kotlinx.android.synthetic.main.community_recyclerview.view.*

class CommunityRecyclerviewAdapter(
    val context: Context,
    val communities: ArrayList<CommunityData>
) :
    RecyclerView.Adapter<CommunityRecyclerviewAdapter.ViewHolder>() {

    override fun getItemCount(): Int = communities.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = communities[position]
        val listener = View.OnClickListener {
            context.startActivity(Intent(context, CommunityContents::class.java))
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
            view.community_index.text = item.community_index

            view.nickname.setOnClickListener(listener)
            view.lecture.setOnClickListener(listener)
            view.community_index.setOnClickListener(listener)
        }
    }
}