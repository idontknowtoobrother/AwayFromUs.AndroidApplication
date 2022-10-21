package com.hexademical.awayfromusapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hexademical.awayfromusapplication.databinding.ActivityUserManagementBinding

class UserManagementActivity : AppCompatActivity() {
    // @ binding
    private lateinit var binding: ActivityUserManagementBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding inflate
        binding = ActivityUserManagementBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // now you not want to use R.findViewById anymore
    }
}