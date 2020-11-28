package com.example.schedulemateadmin.timeschedule

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.schedulemateadmin.R
import com.example.schedulemateadmin.declare.AddBelongRecyclerviewAdapter
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.add_belong.*

class AddBelong :AppCompatActivity(){
    data class AddBelongData(val name:String, val index:String)
    var belongsArray = ArrayList<AddBelongData>()
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home-> onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_belong)

        val getDB = FirebaseDatabase.getInstance().getReference().child("한성대학교")
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val actionBar = getSupportActionBar()
        actionBar!!.setDisplayShowTitleEnabled(false)

        val adapter =
            AddBelongRecyclerviewAdapter(
                this,
                belongsArray
            )
        belong_recyclerview.adapter = adapter
        belong_recyclerview.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        val layoutManager = LinearLayoutManager(this)
        belong_recyclerview.layoutManager = layoutManager

        registerBelong.setOnClickListener {
            if(belongEditText.text.toString() != ""){
                val newData = mutableMapOf<String, String>()
                newData.put(adapter.belongsArray.size.toString(), belongEditText.text.toString())
                getDB.child("info/major").child(adapter.belongsArray.size.toString()).setValue(belongEditText.text.toString())
                belongEditText.text = null
            }
        }
    }
}