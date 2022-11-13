package com.example.myapplication.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.database.entity.ChatEntity
import com.example.myapplication.database.entity.UserEntity

class ListIsiChatAdapter(val usernameLogin:String):RecyclerView.Adapter<ListIsiChatAdapter.ViewHolder>() {

    private var userNameFriend =""
    private var listChat = ArrayList<ChatEntity>()

    fun setDataChat(listChatKirim:ArrayList<ChatEntity>){
        listChat.clear()
        listChat.addAll(listChatKirim)
        notifyDataSetChanged()
    }

    fun setUsernameFriend(user:String){
        userNameFriend=user
    }

    inner class ViewHolder(val view: View):RecyclerView.ViewHolder(view) {
        fun bind(dataChat:ChatEntity){
            view.findViewById<TextView>(R.id.textBubble).text=dataChat.isiChat
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = when (viewType){
            TYPE_PENERIMA -> R.layout.item_bubble_white
            TYPE_PENGIRIM -> R.layout.item_bubble_blue
            else -> throw IllegalArgumentException("invalid type")
        }

        val view= LayoutInflater.from(parent.context).inflate(layout,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listChat[position])
    }

    override fun getItemViewType(position: Int): Int {
       return if(listChat[position].receiverUsername==usernameLogin){
            return TYPE_PENERIMA
        }
       else TYPE_PENGIRIM
    }
    override fun getItemCount(): Int {
        return listChat.size
    }

    companion object {

        private const val TYPE_PENERIMA = 0
        private const val TYPE_PENGIRIM = 1

    }
}




