package com.example.schedulemateadmin.declare.comment

data class CommentData(
    val nickname: String,
    val lecture: String,
    val time: String,
    val reason: String,
    val commentKey: String,
    val lectureKey: String,
    val communityKey: String,
    val title: String,
    val writer: String
)