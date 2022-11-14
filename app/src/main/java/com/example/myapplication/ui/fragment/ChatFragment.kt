package com.example.myapplication.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.database.AppDatabase
import com.example.myapplication.database.DBDao
import com.example.myapplication.database.entity.ChatEntity
import com.example.myapplication.database.entity.FriendsEntity
import com.example.myapplication.database.entity.UserEntity
import com.example.myapplication.databinding.FragmentChatBinding
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.ui.adapter.ListHomeChatAdapter
import com.example.myapplication.ui.adapter.ListIsiChatAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChatFragment(val usernameLogin:String,val namalogin:String,val usernameFriend:String,val namFriend:String) : Fragment() {

    private val coroutine = CoroutineScope(Dispatchers.IO)
    private lateinit var db: AppDatabase
    private lateinit var dao: DBDao

    private lateinit var listChat:ArrayList<ChatEntity>

    private lateinit var adapters: ListIsiChatAdapter
    private lateinit var binding: FragmentChatBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentChatBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db=AppDatabase.build(this.context)
        dao=db.dbDao

        adapters= ListIsiChatAdapter(usernameLogin)
        adapters.setUsernameFriend(usernameFriend)

        binding.rvIsiChat.apply {
            adapter=adapters
            layoutManager=LinearLayoutManager(this.context)
        }
        binding.txtNamaChat.text=namFriend
        refreshAllData()

        binding.imgSendChat.setOnClickListener{
            if(binding.etSendChat.text.toString().isEmpty()){
                Toast.makeText(this.context, "Harap isi pesan", Toast.LENGTH_SHORT).show()
            }
            else{
                val chatEntity=ChatEntity(0,usernameLogin,namalogin,usernameFriend,namFriend,binding.etSendChat.text.toString())
                coroutine.launch {
                    dao.insert(chatEntity)
                    refreshAllData()
                }
            }
        }

    }

    fun refreshAllData(){
        coroutine.launch {
            listChat = dao.getChat() as ArrayList<ChatEntity>
            activity?.runOnUiThread {
                olahData()
            }
        }
    }

    fun olahData(){
        val temp= listChat
        val chatKirim= arrayListOf<ChatEntity>()
        for (i in temp){
            if((i.receiverUsername==usernameLogin &&i.senderUsername==usernameFriend)||(i.senderUsername==usernameLogin &&i.receiverUsername==usernameFriend)){
                chatKirim.add(i)
            }
        }
        binding.etSendChat.text.clear()
        adapters.setDataChat(chatKirim)
        binding.rvIsiChat.scrollToPosition(chatKirim.size-1)
    }

}