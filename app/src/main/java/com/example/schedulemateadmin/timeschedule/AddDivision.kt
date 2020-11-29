package com.example.schedulemateadmin.timeschedule

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.schedulemateadmin.R
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.add_division.*

class AddDivision :AppCompatActivity(){
    data class AddDivisionData(val name:String, val index:String)
    var divisionArray = ArrayList<AddDivisionData>()
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->
                onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_division)

        val getDB = FirebaseDatabase.getInstance().getReference().child("한성대학교")

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val actionBar = getSupportActionBar()
        actionBar!!.setDisplayShowTitleEnabled(false)


        val adapter =
            AddDivisionRecyclerviewAdapter(
                this,
                divisionArray
            )
        division_recyclerview.adapter = adapter
        division_recyclerview.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        val layoutManager = LinearLayoutManager(this)
        division_recyclerview.layoutManager = layoutManager

        registerDivision.setOnClickListener {
            if(divisionEditText.text.toString() != ""){
               getDB.child("info/classification").child(adapter.divisionArray.size.toString()).setValue(divisionEditText.text.toString())
                divisionEditText.text = null
            }
        }
    }
}