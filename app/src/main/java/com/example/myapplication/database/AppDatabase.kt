package com.example.myapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.database.entity.ChatEntity
import com.example.myapplication.database.entity.FriendsEntity
import com.example.myapplication.database.entity.UserEntity
@Database(entities = [
    UserEntity::class, ChatEntity::class, FriendsEntity::class
],version=1)
abstract class AppDatabase : RoomDatabase(){
    //Deklarasi variabel DAO untuk class UserEntity
    abstract val dbDao: DBDao

    //Line di bawah ini mostly hafalan
    companion object {
        private var _database: AppDatabase? = null

        fun build(context:Context?): AppDatabase {
            if(_database == null){
                //
                _database = Room.databaseBuilder(context!!, AppDatabase::class.java,"tugasw7_database").build()
                /*
                Yang penting adalah 1 line di atas ini.
                Line di atas akan membuat sebuah database dengan nama "tutorw7_database".
                Ketika aplikasi pertama kali dirun / diinstall dengan sebuah file AppDatabase,
                program bakal membuat sebuah database dengan tabel sebanyak entity yang dimasukkan
                dalam annotation @Database.

                Nah kalo misal salah buat tabel (column, atau name misal) gimana ko?
                Ada dua cara:
                1. Ngganti yang salah di source codenya. Uninstall aplikasinya di emulatornya.
                Reinstall aplikasi dengan database yang benar
                2. Ngganti yang salah di source codenya. Rerun dengan nama database SELAIN nama
                database sekarang.
                Misal: tutorw7_database -> tutorw7_database2
                 */
            }
            return _database!!
        }
    }
}