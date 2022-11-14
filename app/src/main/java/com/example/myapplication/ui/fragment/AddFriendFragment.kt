package com.example.myapplication.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.database.AppDatabase
import com.example.myapplication.database.DBDao
import com.example.myapplication.database.entity.ChatEntity
import com.example.myapplication.database.entity.FriendsEntity
import com.example.myapplication.database.entity.UserEntity
import com.example.myapplication.databinding.FragmentAddFriendBinding
import com.example.myapplication.databinding.FragmentChatBinding
import com.example.myapplication.ui.adapter.ListIsiChatAdapter
import com.example.myapplication.ui.adapter.ListSearchFriendAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddFriendFragment(val usernameLogin:String,val namalogin:String) : Fragment(),
    ListSearchFriendAdapter.ListSearchFriendCallback {

    private val coroutine = CoroutineScope(Dispatchers.IO)
    private lateinit var db: AppDatabase
    private lateinit var dao: DBDao

    private lateinit var listUser:ArrayList<UserEntity>
    private lateinit var listFriend:ArrayList<FriendsEntity>

    private lateinit var adapters: ListSearchFriendAdapter
    private lateinit var binding: FragmentAddFriendBinding

    private lateinit var usernameSearch:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentAddFriendBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db=AppDatabase.build(this.context)
        dao=db.dbDao

        adapters= ListSearchFriendAdapter(this,usernameLogin)
        binding.rvSearchResult.apply {
            adapter=adapters
            layoutManager=LinearLayoutManager(this.context)
        }
        binding.btnSearch.setOnClickListener {
            usernameSearch=binding.etSearchUsername.text.toString()
            refreshAllData()
        }
        binding.txtResultSearch.text="Result (0)"

    }

    override fun onAddFriend(usernameAdd: String) {
        coroutine.launch {
            val friend=FriendsEntity(0,usernameLogin,usernameAdd)
            dao.insert(friend)
            refreshAllData()
        }
        adapters=ListSearchFriendAdapter(this,usernameLogin)
        binding.rvSearchResult.apply {
            adapter=adapters
            layoutManager= LinearLayoutManager(this.context)
        }

        refreshAllData()
    }

    fun refreshAllData(){
        coroutine.launch {
            listUser= dao.getAllUser() as ArrayList<UserEntity>
            listFriend= dao.getAllFriend() as ArrayList<FriendsEntity>
            activity?.runOnUiThread {
                showData()
            }
        }
    }

    fun showData(){
        var temp=listUser
        var tempFriend=listFriend
        var tempKirim= arrayListOf<UserEntity>()
        for(i in temp){
            if(i.username!=usernameLogin){
                if(i.username.contains(usernameSearch)){
                    tempKirim.add(i)
                }
            }
        }

        adapters.setDataUser(tempKirim)
        adapters.setDataFriend(tempFriend)
        binding.txtResultSearch.text="Result (${tempKirim.size})"
    }


}