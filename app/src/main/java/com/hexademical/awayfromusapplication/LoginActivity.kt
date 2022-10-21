package com.hexademical.awayfromusapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.hexademical.awayfromusapplication.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    // @ binding
    private lateinit var  binding: ActivityLoginBinding

    // @ debug tag
    private var TAG = "LoginActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding inflate
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // now you not want to use R.findViewById anymore

        initHandler() // start initialize handler
    }


    // @ Initialize Handler
    fun initHandler() {

        // login handler
        binding?.loginBtn?.setOnClickListener {
            // get username and password
            val username = binding.lUsername.text.toString()
            val password = binding.lPassword.text.toString()
            Log.d(TAG, "username: ${username} password: ${password}")
        }

    }


}