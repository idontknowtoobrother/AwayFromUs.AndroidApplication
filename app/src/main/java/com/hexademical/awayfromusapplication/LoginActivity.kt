package com.hexademical.awayfromusapplication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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

    // @ User
    private lateinit var user: UserResponse

    // @ Token
    private lateinit var x_access_token: String

    // @ debug tag
    private var TAG = "LoginActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding inflate
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initHandler() // start initialize handler
        _loadSavedToken() // load x-access-token if user already login one time <3
        _getUserData() // try using auth token login
    }


    // @ Initialize Handler
    private fun initHandler() {

        // login handler
        binding?.loginBtn?.setOnClickListener {
            // get username and password
            val username = binding.lUsername.text.toString().trim()
            val password = binding.lPassword.text.toString().trim()
            Log.d(TAG, "username: ${username} password: ${password}")
            login(username, password)
        }

    }


    private fun login(_username: String, _password: String) {
        val request = UserRequest()
        request.username = _username
        request.password = _password
        val retro = Retro().getRetroClientInstance().create(UserApi::class.java)

        val loginThis = this
        retro.login(request).enqueue(object : Callback<UserResponse>{

            override fun onResponse(call: Call<UserResponse>?, response: Response<UserResponse>?) {
                if(response != null){
                    if(response.isSuccessful()){
                        user = response.body()
                        x_access_token = user.getToken().toString()

                        // @ Save token process
                        val sharedPreferences: SharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
                        val editor : SharedPreferences.Editor = sharedPreferences.edit()
                        editor.apply {
                            putString("x-access-token", x_access_token)
                        }.apply()

                        Log.d(TAG, "Token: ${user.getToken()}")
                        Log.d(TAG, "Username: ${user.getUsername()}")
                        Log.d(TAG, "Fullname: ${user.getFullname()}")
                        Log.d(TAG, "Resources: ${user.getResoures()}")

                        val intent = Intent(loginThis, UserManagementActivity::class.java).also {
                            startActivity(it)
                        }

                    }else if(response.code() == 400) {
                        Toast.makeText(applicationContext, "Invalid Username and Password", Toast.LENGTH_SHORT).show()
                        Log.e(TAG, "invalid username and password")
                    }else{
                        Toast.makeText(applicationContext, "Server Error", Toast.LENGTH_SHORT).show()
                        Log.e(TAG, "server error")
                    }
                }

            }

            override fun onFailure(call: Call<UserResponse>?, t: Throwable?) {
                Log.e(TAG, "error: ${t?.message}")
            }

        })
    }

    private fun _loadSavedToken() {
        // @ Get token process
        val sharedPreferences: SharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        x_access_token = sharedPreferences.getString("x-access-token", "").toString()
    }

    private fun _getUserData() {
        if(x_access_token == ""){
            return
        }

        val retro = Retro().getRetroClientInstance().create(UserApi::class.java)
        // @ test token

        val loginThis = this
        retro.getUserData(x_access_token).enqueue(object : Callback<UserResponse>{
            override fun onResponse(call: Call<UserResponse>?, response: Response<UserResponse>?) {
               if(response != null){
                   if(response.isSuccessful()){
                       if(response.code() == 200) {
                           user = response.body()
                           Log.d(TAG, "Username: ${user.getUsername()} Already Login :D")
                           val intent = Intent(loginThis, UserManagementActivity::class.java).also {
                               startActivity(it)
                           }
                       }
                   }
               }
            }

            override fun onFailure(call: Call<UserResponse>?, t: Throwable?) {
                Log.e(TAG, "error: ${t?.message}")
            }
        })

    }


}