package com.example.myapplication.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
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

class HomeFragment(private val usernameLogin:String, val namalogin:String) : Fragment() {


    private val coroutine = CoroutineScope(Dispatchers.IO)
    private lateinit var db: AppDatabase
    private lateinit var dao: DBDao

    private lateinit var listUser:ArrayList<UserEntity>
    private lateinit var listFriend:ArrayList<FriendsEntity>
    private lateinit var listChat:ArrayList<ChatEntity>

    private lateinit var adapters:ListHomeChatAdapter
    private lateinit var binding:FragmentHomeBinding

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

    }


}