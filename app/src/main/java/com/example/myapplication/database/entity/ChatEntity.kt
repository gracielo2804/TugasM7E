package com.example.myapplication.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat")
data class ChatEntity(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    var senderUsername : String,
    var senderName : String,
    var receiverUsername:String,
    var receiverName:String,
    var isiChat:String
)
