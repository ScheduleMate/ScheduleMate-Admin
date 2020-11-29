package com.example.schedulemateadmin.timeschedule

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.schedulemateadmin.R
import com.example.schedulemateadmin.declare.SubjectLectureRecyclerviewAdapter
import com.example.schedulemateadmin.declare.TimeScheduleListRecyclerviewAdapter
import com.google.firebase.components.Dependency
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.add_subject.*
import java.lang.StringBuilder

class AddSubject : AppCompatActivity() {
    var lectures = ArrayList<SubjectLectureData>()
    var subjectPushReference: DatabaseReference? = null
    var classInfoReference: DatabaseReference? = null
    var newData = RegisterSubject("", "", "", "", "", ArrayList<String>(), 0)
    lateinit var semester: String
    lateinit var university: String
    lateinit var root: DatabaseReference


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home ->
                moveTo(Intent(this, TimeScheduleList::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_subject)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        semester = intent.getStringExtra("semester")
        university = intent.getStringExtra("university")
        root = FirebaseDatabase.getInstance().reference.child(university)

        val actionBar = getSupportActionBar()
        actionBar!!.setDisplayShowTitleEnabled(false)

        var belongArray = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1)
        var belongIndex = 0
        root.child("/info/major").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (belong in snapshot.children) {
                    belongArray.add(belong.value.toString())
                    belongIndex++
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
        belongs.adapter = belongArray
        belongs.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                newData.dependency = belongs.selectedItem.toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        var divisionArray = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1)
        var divisionIndex = 0
        root.child("/info/classification")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (division in snapshot.children) {
                        divisionArray.add(division.value.toString())
                        divisionIndex++
                    }
                }
                override fun onCancelled(error: DatabaseError) {}
            })
        divisions.adapter = divisionArray
        divisions.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                newData.classification = divisions.selectedItem.toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        var creditsArray = resources.getStringArray(R.array.credits)
        var creditsAdapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, creditsArray)
        credits.adapter = creditsAdapter
        credits.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                newData.credit = credits.selectedItem.toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        var gradesArray = resources.getStringArray(R.array.grades)
        var gradesAdapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, gradesArray)
        grades.adapter = gradesAdapter
        grades.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                newData.grade = grades.selectedItem.toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        // 등록되어 있던 과목일 경우
        if (intent.getStringExtra("subjectPushKey") != null) {
            var subjectPushKey = intent.getStringExtra("subjectPushKey")
            classInfoReference = root.child("/$semester/classInfo")
            val subject = root.child(semester).child("subject")
            subjectPushReference = subject.child(intent.getStringExtra("subjectPushKey"))
            subjectPushReference!!.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    subject_name.setText(snapshot.child("title").value.toString())
                    for (i in 0..belongIndex - 1) {
                        if (snapshot.child("dependency").value.toString() == belongArray.getItem(i))
                            belongs.setSelection(i)
                    }
                    for (i in 0..divisionIndex - 1) {
                        if (snapshot.child("classification").value.toString() == divisionArray.getItem(i))
                            divisions.setSelection(i)
                    }

                    for (i in 0..creditsArray.size - 1) {
                        if (snapshot.child("credit").value.toString() == creditsArray[i])
                            credits.setSelection(i)
                    }
                    for (i in 0..gradesArray.size - 1) {
                        if (snapshot.child("grade").value.toString() == gradesArray[i])
                            grades.setSelection(i)
                    }
                }
                override fun onCancelled(error: DatabaseError) {}
            })
            addLecture.setOnClickListener {
                subjectPushReference!!.child("title").setValue(subject_name.text.toString())
                var intent = Intent(this, AddLecture::class.java)
                intent.putExtra("subjectPushKey", subjectPushKey)
                intent.putExtra("semester", semester)
                intent.putExtra("university", university)
                intent.putExtra("title", subject_name.text.toString())

                startActivity(intent)
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            }
            register.setOnClickListener {
                if (subject_name.text.toString() == "") {
                    Toast.makeText(this, "과목명을 입력해주세요.", Toast.LENGTH_LONG).show()
                } else {
                    subjectPushReference!!.child("title").setValue(subject_name.text.toString())
                    var intent = Intent(this, TimeScheduleList::class.java)
                    subject.child("/$subjectPushKey/credit").setValue(newData.credit)
                    subject.child("/$subjectPushKey/classification")
                        .setValue(newData.classification)
                    subject.child("/$subjectPushKey/dependency").setValue(newData.dependency)
                    subject.child("/$subjectPushKey/grade").setValue(newData.grade)
                    subject.child("/$subjectPushKey/title").setValue(subject_name.text.toString())

                    intent.putExtra("semester", semester)
                    intent.putExtra("university", university)
                    startActivity(intent)
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                }
            }
        } else {  // 등록되어있지 않은 과목일 경우
            var subject = root.child(semester).child("subject")
            var newSubjectPushKey = subject.push().key

            addLecture.setOnClickListener {
                if (subject_name.text.toString() == "") {
                    Toast.makeText(this, "과목명을 입력해주세요.", Toast.LENGTH_LONG).show()
                } else {
                    var intent = Intent(this, AddLecture::class.java)
                    subject.child("/$newSubjectPushKey/credit").setValue(newData.credit)
                    subject.child("/$newSubjectPushKey/classification")
                        .setValue(newData.classification)
                    subject.child("/$newSubjectPushKey/dependency").setValue(newData.dependency)
                    subject.child("/$newSubjectPushKey/grade").setValue(newData.grade)
                    subject.child("/$newSubjectPushKey/title").setValue(subject_name.text.toString())

                    intent.putExtra("semester", semester)
                    intent.putExtra("university", university)
                    intent.putExtra("subjectPushKey", newSubjectPushKey)
                    intent.putExtra("title", subject_name.text.toString())
                    startActivity(intent)
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                }
            }
            register.setOnClickListener {
                if (subject_name.text.toString() == "") {
                    Toast.makeText(this, "과목명을 입력해주세요.", Toast.LENGTH_LONG).show()
                } else {
                    var intent = Intent(this, TimeScheduleList::class.java)
                    subject.child("/$newSubjectPushKey/credit").setValue(newData.credit)
                    subject.child("/$newSubjectPushKey/classification")
                        .setValue(newData.classification)
                    subject.child("/$newSubjectPushKey/dependency").setValue(newData.dependency)
                    subject.child("/$newSubjectPushKey/grade").setValue(newData.grade)
                    subject.child("/$newSubjectPushKey/title").setValue(subject_name.text.toString())

                    intent.putExtra("semester", semester)
                    intent.putExtra("university", university)
                    startActivity(intent)
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                }
            }
        }

        add_lecture_recyclerview.adapter =
            SubjectLectureRecyclerviewAdapter(this, lectures, subjectPushReference, classInfoReference, university)
        val layoutManager = LinearLayoutManager(this)
        add_lecture_recyclerview.layoutManager = layoutManager

        delete.setOnClickListener {
            val popUp = AlertDialog.Builder(this)
            popUp.setTitle("해당 과목을 삭제하시겠습니까?")
            popUp.setPositiveButton(
                "삭제",
                { dialog: DialogInterface?, which: Int ->
                    val subjectPushKey = intent.getStringExtra("subjectPushKey")
                    if (subjectPushKey != null) {
                        root.child("/$semester/subject/$subjectPushKey/class")
                            .addValueEventListener(object : ValueEventListener {
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    for (s in snapshot.children) {
                                        var key = s.key
                                        root.child("/$semester/classInfo/").child(key!!)
                                            .removeValue()
                                    }
                                }
                                override fun onCancelled(error: DatabaseError) {}
                            })
                        root.child("/$semester/subject/$subjectPushKey").removeValue()
                    }
                    var intent = Intent(this, TimeScheduleList::class.java)
                    intent.putExtra("semester", semester)
                    intent.putExtra("university", university)
                    startActivity(intent)
                }
            )
            popUp.setNegativeButton("취소", { dialog: DialogInterface?, which: Int -> })
            popUp.setCancelable(false)
            popUp.show()
        }

    }

    fun moveTo(intent: Intent) {
        intent.putExtra("semester", semester)
        intent.putExtra("university", university)
        Toast.makeText(this, "변경사항은 자동으로 저장됩니다.", Toast.LENGTH_LONG).show()
        startActivity(intent)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
    }

    data class RegisterSubject(
        var title: String,
        var grade: String,
        var dependency: String,
        var credit: String,
        var classification: String,
        var classes: ArrayList<String>,
        var index: Int
    )
}