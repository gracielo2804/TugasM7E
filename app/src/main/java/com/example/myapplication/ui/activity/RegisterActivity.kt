package com.example.myapplication.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.myapplication.database.AppDatabase
import com.example.myapplication.database.DBDao
import com.example.myapplication.database.entity.UserEntity
import com.example.myapplication.databinding.ActivityRegisterBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private lateinit var toLogin: Button;
    private lateinit var register: Button;
    private lateinit var txtNama: TextView;
    private lateinit var txtUsername: TextView;
    private lateinit var txtPassword: TextView;
    lateinit var listUser:ArrayList<UserEntity>;


    private val coroutine = CoroutineScope(Dispatchers.IO)
    private lateinit var db: AppDatabase
    private lateinit var dao: DBDao

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toLogin = binding.GoLogin
        register = binding.Regis
        txtNama = binding.Name
        txtUsername = binding.Usernames
        txtPassword = binding.Pass
        listUser= ArrayList()

        db=AppDatabase.build(this)
        dao=db.dbDao

        coroutine.launch {
            listUser= dao.getAllUser() as ArrayList<UserEntity>
        }

        toLogin.setOnClickListener {
            val login = Intent(this, LoginActivity::class.java);
            startActivity(login);
            finish()
        }

        register.setOnClickListener {
            if(txtUsername.text.toString() =="" || txtUsername.text.toString() == "" || txtNama.text.toString() ==""){
                Toast.makeText(this, "Harap isi semua kolom", Toast.LENGTH_SHORT).show()
            }
            else{
                var bol = true;
                for (i in 0 until listUser.size){
                    if (txtUsername.text.toString() == listUser[i].username){
                        bol = false
                    }
                }
                if (bol){
                    val users = UserEntity(
                        txtUsername.text.toString(),txtPassword.text.toString(),txtNama.text.toString()
                    );
                    coroutine.launch {
                        dao.insert(users)
                        listUser.add(users);
                        runOnUiThread{
                            Toast.makeText(this@RegisterActivity, "Register Berhasil", Toast.LENGTH_SHORT).show()
                            txtNama.setText("")
                            txtUsername.setText("")
                            txtPassword.setText("")
                        }
                    }
                }

                if (!bol){
                    Toast.makeText(this, "Username Telah Terdaftar", Toast.LENGTH_SHORT).show();
                }
            }

        }

    }
}