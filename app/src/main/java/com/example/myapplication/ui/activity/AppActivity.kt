package com.example.myapplication.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityAppBinding
import com.example.myapplication.ui.fragment.AddFriendFragment
import com.example.myapplication.ui.fragment.ChatFragment
import com.example.myapplication.ui.fragment.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class AppActivity : AppCompatActivity() {

    var usernamelogin:String=""
    var namalogin:String=""

    lateinit var addFriendFragment: AddFriendFragment
    lateinit var chatFragment: ChatFragment
    lateinit var homeFragment: HomeFragment

    lateinit var fragmentManager: FragmentTransaction

    private lateinit var binding: ActivityAppBinding
    var bundle = Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivityAppBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle=intent.extras
        if(intent.hasExtra("usernamelogin")){
            Log.e("Cek Intent","masuk")
            usernamelogin=intent.getStringExtra("usernamelogin").toString()
            namalogin=intent.getStringExtra("namalogin").toString()
        }


        fragmentManager=supportFragmentManager.beginTransaction()
        addFriendFragment= AddFriendFragment(usernamelogin,namalogin)
        chatFragment=ChatFragment(usernamelogin,namalogin,"")
        homeFragment=HomeFragment(usernamelogin,namalogin)

        val navView: BottomNavigationView = binding.bottomNav
        homeFragment.arguments = bundle
        fragmentManager.replace(R.id.containerFragment,homeFragment)
        fragmentManager.commit()

        navView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.item_add ->{
                    fragmentManager = supportFragmentManager.beginTransaction()
                    addFriendFragment.arguments = bundle
                    fragmentManager.replace(R.id.containerFragment,addFriendFragment)
                    fragmentManager.commit()
                    true
                }
                R.id.item_home ->{
                    fragmentManager = supportFragmentManager.beginTransaction()
                    homeFragment.arguments = bundle
                    fragmentManager.replace(R.id.containerFragment,homeFragment)
                    fragmentManager.commit()
                    true
                }
                else -> {
                    true
                }
            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_logout,menu)
        return super.onCreateOptionsMenu(menu)
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.item_logout ->{
                var customerLogout = Intent(this, LoginActivity::class.java);
                customerLogout.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                Toast.makeText(this, "Berhasil Logout", Toast.LENGTH_SHORT).show()
                finish()
                startActivity(customerLogout);
            }
        }
        return super.onOptionsItemSelected(item)
    }


}