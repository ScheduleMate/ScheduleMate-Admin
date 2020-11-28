package com.example.schedulemateadmin.declare.publicSchedule

data class PublicScheduleData(
    val nickname: String,
    val lecture: String,
    val time: String,
    val scheduleKey: String,
    val lectureKey: String,
    val semester:String,
    val writer:String
)