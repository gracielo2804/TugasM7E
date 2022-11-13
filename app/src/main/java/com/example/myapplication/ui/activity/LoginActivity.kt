package com.example.myapplication.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.myapplication.R
import com.example.myapplication.database.AppDatabase
import com.example.myapplication.database.DBDao
import com.example.myapplication.database.entity.UserEntity
import com.example.myapplication.databinding.ActivityLoginBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var toRegister: Button;
    private lateinit var login: Button;
    private lateinit var username: TextView;
    private lateinit var passwords: TextView;
    lateinit var listUser:ArrayList<UserEntity>;


    private val coroutine = CoroutineScope(Dispatchers.IO)
    private lateinit var db: AppDatabase
    private lateinit var dao: DBDao

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listUser = ArrayList();
        toRegister = binding.Register
        login = binding.Login;
        username = binding.username
        passwords = binding.password

        db=AppDatabase.build(this)
        dao=db.dbDao
        coroutine.launch {
            listUser= dao.getAllUser() as ArrayList<UserEntity>
            if(listUser.size==0){
                var userEntity=UserEntity("dosen","dosen","Dosen Femes")
                dao.insert(userEntity)
                listUser.add(userEntity)
                userEntity=UserEntity("guru","guru","Guru Besar")
                dao.insert(userEntity)
                listUser.add(userEntity)
                userEntity=UserEntity("abc","abc","Abcdef")
                dao.insert(userEntity)
                listUser.add(userEntity)
                userEntity=UserEntity("mhs","mhs","Mahasewa")
                dao.insert(userEntity)
                listUser.add(userEntity)
                listUser.add(userEntity)
                userEntity=UserEntity("asd","asd","ASDFGHJKL")
                dao.insert(userEntity)
                listUser.add(userEntity)
            }
        }

        login.setOnClickListener {
            //Toast.makeText(this, username.text, Toast.LENGTH_SHORT).show()
            if(username.text.toString() =="" || passwords.text.toString() == ""){
                Toast.makeText(this, "Username dan Password Tidak Boleh Kosong", Toast.LENGTH_SHORT).show()
            }
            else{
                if(listUser.size>0){
                    var idx = 0;
                    var cekuser=false
                    for (i in 0 until listUser.size){
                        if(username.text.toString() == listUser[i].username && listUser[i].password.equals(passwords.text.toString())) {
                            idx = i
                            cekuser=true
                        }

                    }

                    if(cekuser){
                        val toHomeNewIntent = Intent(this, AppActivity::class.java);
                        toHomeNewIntent.putExtra("usernamelogin",listUser[idx].username);
                        toHomeNewIntent.putExtra("namalogin",listUser[idx].name);
                        startActivity(toHomeNewIntent);
                    }
                    else{
                        Toast.makeText(this, "Username Tidak Ditemukan atau password salah", Toast.LENGTH_SHORT).show()
                    }
                }
                else{
                    Toast.makeText(this, "Username Tidak Ditemukan atau password salah", Toast.LENGTH_SHORT).show()
                }

            }
        }
        toRegister.setOnClickListener {
            val Registers = Intent(this, RegisterActivity::class.java);
            startActivity(Registers);
            finish()
        }
    }

}