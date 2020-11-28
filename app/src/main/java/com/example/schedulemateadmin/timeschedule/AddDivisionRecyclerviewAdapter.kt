package com.example.schedulemateadmin.timeschedule

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.example.schedulemateadmin.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.division_recyclerview.view.*


class AddDivisionRecyclerviewAdapter(
    val context: Context,
    val divisionArray: ArrayList<AddDivision.AddDivisionData>
) :
    RecyclerView.Adapter<AddDivisionRecyclerviewAdapter.ViewHolder>() {
    val getDB = FirebaseDatabase.getInstance().reference.child("한성대학교")
    init {
        getDB.child("info/classification").addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(divisionArray.size != 0){
                    divisionArray.clear()

                }
                for(division in snapshot.children){
                    divisionArray.add(AddDivision.AddDivisionData(division.value.toString(), division.key.toString()))
                }
                notifyDataSetChanged()

            }
            override fun onCancelled(error: DatabaseError) { }
        })
    }

    override fun getItemCount(): Int = divisionArray.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = divisionArray[position]
        val listener = View.OnClickListener{
            val popUp = AlertDialog.Builder(context)
            popUp.setTitle(item.name + "을/를 삭제하시겠습니까?")
            popUp.setPositiveButton(
                "삭제",
                { dialog: DialogInterface?, which: Int ->
                    getDB.child("info/classification").child(item.index).removeValue()
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
            .inflate(R.layout.division_recyclerview, parent, false)
        return ViewHolder(view)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var view: View = view

        fun bind(listener: View.OnClickListener, item: AddDivision.AddDivisionData) {
            view.division.text = item.name
            view.division.setOnClickListener(listener)
        }
    }
}