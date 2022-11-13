package com.example.myapplication.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "friend")
class FriendsEntity (
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    var username1 : String,
    var username2 : String,
)