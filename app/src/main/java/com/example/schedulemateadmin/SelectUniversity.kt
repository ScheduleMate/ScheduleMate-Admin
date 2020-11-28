package com.example.schedulemateadmin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.schedulemateadmin.declare.SelectUniversityRecyclerview
import kotlinx.android.synthetic.main.select_university.*

class SelectUniversity :AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.select_university)

        val adapter = SelectUniversityRecyclerview(this)
        selectUniversity.adapter = adapter
        selectUniversity.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        val layoutManager = LinearLayoutManager(this)
        selectUniversity.layoutManager = layoutManager
    }
}