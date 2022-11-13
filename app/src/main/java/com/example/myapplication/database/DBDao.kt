package com.example.myapplication.database

import androidx.room.*
import com.example.myapplication.database.entity.ChatEntity
import com.example.myapplication.database.entity.FriendsEntity
import com.example.myapplication.database.entity.UserEntity

@Dao
interface DBDao {
    @Insert
    suspend fun insert(user: UserEntity)

    @Update
    suspend fun update(user: UserEntity)

    @Delete
    suspend fun delete(user: UserEntity)

    @Query("DELETE FROM users where username = :username")
    suspend fun deleteUser(username: String)

    @Query("SELECT * FROM users")
    suspend fun getAllUser():List<UserEntity>

    @Query("SELECT * FROM users where username = :username")
    suspend fun get(username:String): UserEntity?

    @Query("SELECT * FROM users where username like '%'||:username||'%'")
    suspend fun getUserSearch(username:String): List<UserEntity>?



    @Insert
    suspend fun insert(user: FriendsEntity)

    @Update
    suspend fun update(user: FriendsEntity)

    @Delete
    suspend fun delete(user: FriendsEntity)

    @Query("DELETE FROM friend where id = :id")
    suspend fun deleteFriend(id: String)

    @Query("SELECT * FROM friend")
    suspend fun getAllFriend():List<FriendsEntity>

    @Query("SELECT * FROM friend where id = :id")
    suspend fun getFriendByID(id:String): FriendsEntity?

    @Query("SELECT count(*) FROM friend where id = :id")
    suspend fun getCountFriend(id:Int):Int



    @Insert
    suspend fun insert(user: ChatEntity)

    @Update
    suspend fun update(user: ChatEntity)

    @Delete
    suspend fun delete(user: ChatEntity)

    @Query("DELETE FROM chat where id = :id")
    suspend fun deleteKelas(id: String)


    @Query("SELECT * FROM chat")
    suspend fun getKelas():List<ChatEntity>




}