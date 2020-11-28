package com.example.schedulemateadmin.declare

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.example.schedulemateadmin.R
import com.example.schedulemateadmin.timeschedule.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.belong_recyclerview.view.*


class AddBelongRecyclerviewAdapter(
    val context: Context,
    val belongsArray: ArrayList<AddBelong.AddBelongData>
) :
    RecyclerView.Adapter<AddBelongRecyclerviewAdapter.ViewHolder>() {
    val getDB = FirebaseDatabase.getInstance().reference.child("한성대학교")
    init {
        getDB.child("info/major").addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(belongsArray.size != 0){
                    belongsArray.clear()
                }
                for(belong in snapshot.children){
                    belongsArray.add(AddBelong.AddBelongData(belong.value.toString(), belong.key.toString()))
                }
                notifyDataSetChanged()

            }
            override fun onCancelled(error: DatabaseError) { }
        })
    }

    override fun getItemCount(): Int = belongsArray.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = belongsArray[position]
        val listener = View.OnClickListener{
            val popUp = AlertDialog.Builder(context)
            popUp.setTitle(item.name + "을/를 삭제하시겠습니까?")
            popUp.setPositiveButton(
                "삭제",
                { dialog: DialogInterface?, which: Int ->
                    getDB.child("info/major").child(item.index).removeValue()

                }
            )
            popUp.setNegativeButton(
                "취소",
                { dialog: DialogInterface?, which: Int ->

                }
            )
            popUp.setCancelable(false)
            popUp.show()
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
            .inflate(R.layout.belong_recyclerview, parent, false)


        return ViewHolder(view)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var view: View = view

        fun bind(listener: View.OnClickListener, item: AddBelong.AddBelongData) {
            view.belong.text = item.name
            view.belong.setOnClickListener(listener)
        }
    }
}