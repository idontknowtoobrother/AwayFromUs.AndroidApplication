package com.hexademical.awayfromusapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.hexademical.awayfromusapplication.API.UserRequest
import com.hexademical.awayfromusapplication.API.UserResponse
import com.hexademical.awayfromusapplication.Interface.UserApi
import com.hexademical.awayfromusapplication.Retrofit.Retro
import com.hexademical.awayfromusapplication.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
            val username = binding.lUsername.text.toString().trim()
            val password = binding.lPassword.text.toString().trim()
            Log.d(TAG, "username: ${username} password: ${password}")
            login(username, password)
        }

    }


    fun login(_username: String, _password: String) {
        val request = UserRequest()
        request.username = _username
        request.password = _password
        val retro = Retro().getRetroClientInstance().create(UserApi::class.java)
        retro.login(request).enqueue(object : Callback<UserResponse>{

            override fun onResponse(call: Call<UserResponse>?, response: Response<UserResponse>?) {
                if(response != null){
                    if(response.isSuccessful()){
                        val user = response.body()
                        Log.d(TAG, "Token: ${user.getToken()}")
                        Log.d(TAG, "Username: ${user.getUsername()}")
                        Log.d(TAG, "Fullname: ${user.getFullname()}")
                        Log.d(TAG, "Resources: ${user.getResoures()}")
                    }else if(response.code() == 400) {
                        Log.e(TAG, "invalid username and password")
                    }else{
                        Log.e(TAG, "server error")
                    }
                }

            }

            override fun onFailure(call: Call<UserResponse>?, t: Throwable?) {
                Log.e(TAG, "error")
            }

        })
    }


}