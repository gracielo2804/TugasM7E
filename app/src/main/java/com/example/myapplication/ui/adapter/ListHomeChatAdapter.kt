package com.example.myapplication.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.database.entity.ChatEntity
import com.example.myapplication.database.entity.FriendsEntity
import com.example.myapplication.database.entity.UserEntity

class ListHomeChatAdapter(val callback:ListChatFriendCallback,val  usernameLogin:String): RecyclerView.Adapter<ListHomeChatAdapter.ViewHolder>() {

    private var listChat = ArrayList<ChatEntity>()
    private var listFriend = ArrayList<FriendsEntity>()
    private var listUser = ArrayList<UserEntity>()

    fun setFriend(listFriendKirim:ArrayList<FriendsEntity>){
        listFriend.clear()
        listFriend.addAll(listFriendKirim)
        notifyDataSetChanged()
    }
    fun setChat(listChatKirim:ArrayList<ChatEntity>){
        listChat.clear()
        listChat.addAll(listChatKirim)
        notifyDataSetChanged()
    }
    fun setUser(listUserKirim:ArrayList<UserEntity>){
        listUser.clear()
        listUser.addAll(listUserKirim)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val view: View):RecyclerView.ViewHolder(view) {
        val txtName: TextView =view.findViewById(R.id.txtNameListHome)
        val txtChat: TextView =view.findViewById(R.id.txtIsiChatListHome)

        fun bind(friendsEntity: FriendsEntity){
            var usernameFriend=""
            var namefriend=""
            if(friendsEntity.username1==usernameLogin)usernameFriend=friendsEntity.username2
            else usernameFriend=friendsEntity.username1

            for(i in listUser){
                if(i.username==usernameFriend){
                    txtName.text=i.name;
                    namefriend=i.name
                }
            }
            var isiChat="(Empty Chat Room)"

            for (i in listChat){
                if((i.receiverUsername==usernameLogin &&i.senderUsername==usernameFriend)||(i.senderUsername==usernameLogin &&i.receiverUsername==usernameFriend)){
                    isiChat=i.isiChat
                }
            }
            txtChat.text=isiChat
            view.setOnClickListener {
                callback.onChatClicked(usernameFriend,namefriend)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_item_list_chat, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listFriend[position])
    }

    override fun getItemCount(): Int {
        return listFriend.size
    }

    interface ListChatFriendCallback {
        fun onChatClicked(usernameFriend:String,namaFriend:String)
    }



}