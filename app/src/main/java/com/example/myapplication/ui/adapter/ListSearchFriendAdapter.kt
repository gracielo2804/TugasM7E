package com.example.myapplication.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.database.entity.FriendsEntity
import com.example.myapplication.database.entity.UserEntity
import com.example.myapplication.databinding.LayoutItemSearchFriendBinding

class ListSearchFriendAdapter(val callback:ListSearchFriendCallback, val usernameLogin:String): RecyclerView.Adapter<ListSearchFriendAdapter.ViewHolder>(){

    private var listUser = ArrayList<UserEntity>()
    private var listFriend = ArrayList<FriendsEntity>()

    fun setDataUser(listUserEntity: ArrayList<UserEntity>){
        listUser.clear()
        listUser.addAll(listUserEntity)
        notifyDataSetChanged()
    }

    fun setDataFriend(listFriendsEntity: ArrayList<FriendsEntity>){
        listFriend.clear()
        listFriend.addAll(listFriendsEntity)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)  {
        private val binding = LayoutItemSearchFriendBinding.bind(view)
        fun bind(user:UserEntity){
            binding.txtUsernameUserListSearch.text=user.username
            binding.txtNameUserListSearch.text=user.name
            binding.imgAddFriend.setImageResource(R.drawable.person_add)
            for (i in listFriend){
                if((i.username1==usernameLogin &&i.username2==user.username)||(i.username2==usernameLogin &&i.username1==user.username)){
                    binding.imgAddFriend.setImageResource(R.drawable.person_added)
                    binding.imgAddFriend.tag = "added"
                }
            }
            binding.imgAddFriend.setOnClickListener {
                if(it.tag!="added"){
                    callback.onAddFriend(user.username)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder=
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_item_search_friend, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    override fun getItemCount(): Int {
        return listUser.size
    }
    interface ListSearchFriendCallback {
        fun onAddFriend(usernameAdd:String)
    }
}


