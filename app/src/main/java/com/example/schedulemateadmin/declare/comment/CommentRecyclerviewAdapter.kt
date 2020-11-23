package com.example.schedulemateadmin.declare.comment

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.schedulemateadmin.R
import kotlinx.android.synthetic.main.comment_recyclerview.view.*

class CommentRecyclerviewAdapter(val context:Context, val comments: ArrayList<CommentData>) :
    RecyclerView.Adapter<CommentRecyclerviewAdapter.ViewHolder>() {

    override fun getItemCount(): Int = comments.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = comments[position]
        val listener =  View.OnClickListener{
            context.startActivity(Intent(context, CommentContents::class.java))
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
            .inflate(R.layout.comment_recyclerview, parent, false)

        return ViewHolder(view)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var view: View = view

        fun bind(listener: View.OnClickListener, item: CommentData) {
            view.nickname.text = item.nickname
            view.lecture.text = item.lecture
            view.community_index.text = item.community_index
            view.comment_index.text = item.comment_index

            view.nickname.setOnClickListener(listener)
            view.lecture.setOnClickListener(listener)
            view.community_index.setOnClickListener(listener)
            view.comment_index.setOnClickListener(listener)
        }
    }
}