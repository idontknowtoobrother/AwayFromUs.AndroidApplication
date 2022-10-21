package com.hexademical.awayfromusapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hexademical.awayfromusapplication.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    // @ binding
    private lateinit var  binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding inflate
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // now you not want to use R.findViewById anymore
    }
}