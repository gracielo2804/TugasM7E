package com.example.myapplication.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.database.AppDatabase
import com.example.myapplication.database.DBDao
import com.example.myapplication.database.entity.ChatEntity
import com.example.myapplication.database.entity.FriendsEntity
import com.example.myapplication.database.entity.UserEntity
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.ui.adapter.ListHomeChatAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment(private val usernameLogin:String, val namalogin:String) : Fragment(),
    ListHomeChatAdapter.ListChatFriendCallback {



    private val coroutine = CoroutineScope(Dispatchers.IO)
    private lateinit var db: AppDatabase
    private lateinit var dao: DBDao

    private lateinit var listUser:ArrayList<UserEntity>
    private lateinit var listFriend:ArrayList<FriendsEntity>
    private lateinit var listChat:ArrayList<ChatEntity>

    private lateinit var adapters:ListHomeChatAdapter
    private lateinit var binding:FragmentHomeBinding

    var onFriendClickListener:((usernameFriend: String,namaFriend:String)-> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.txtNamaHome.text=namalogin
        binding.txtUsernameHome.text=usernameLogin

        db=AppDatabase.build(this.context)
        dao=db.dbDao

        adapters=ListHomeChatAdapter(this,usernameLogin)

        binding.rvListChaHome.apply {
            adapter=adapters
            layoutManager=LinearLayoutManager(this.context)
        }
        refreshAllData()

    }


    fun refreshAllData(){
        coroutine.launch {
            listChat= dao.getChat() as ArrayList<ChatEntity>
            listUser= dao.getAllUser() as ArrayList<UserEntity>
            listFriend= dao.getAllFriend() as ArrayList<FriendsEntity>
            activity?.runOnUiThread {
                olahData()
            }
        }
    }

    fun olahData(){
        val tempUser=listUser
        val tempFriend=listFriend
        val tempChat=listChat
        val friendKirim= arrayListOf<FriendsEntity>()
        adapters.setChat(tempChat)
        adapters.setUser(tempUser)
        for(i in tempFriend){
            if(i.username1==usernameLogin||i.username2==usernameLogin){
                friendKirim.add(i)
            }
        }
        adapters.setFriend(friendKirim)
        binding.txtJumlahFriend.text="Friends (${friendKirim.size})"
    }

    override fun onChatClicked(usernameFriend: String,namaFriend: String) {
        Log.e("namaFriend",namaFriend)
        onFriendClickListener?.invoke(usernameFriend,namaFriend)
    }


}