package com.hexademical.awayfromusapplication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.hexademical.awayfromusapplication.API.UserResponse
import com.hexademical.awayfromusapplication.Interface.UserApi
import com.hexademical.awayfromusapplication.Retrofit.Retro
import com.hexademical.awayfromusapplication.databinding.ActivityUserManagementBinding
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class UserManagementActivity : AppCompatActivity() {
    // @ binding
    private lateinit var binding: ActivityUserManagementBinding

    // @ User
    private lateinit var user: UserResponse

    // @ Token
    private lateinit var x_access_token: String

    // @ debug tag
    private var TAG = "UserManagementActivity"

    // @ Coroutine for refreshing user data
    private val userThread = CoroutineScope(CoroutineName("userDataThread"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding inflate
        binding = ActivityUserManagementBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // now you not want to use R.findViewById anymore

        initHandler()
        _loadSavedToken()
        _tickRefreshUserData()
    }

    private fun _loadUserData() {
        val retro = Retro().getRetroClientInstance().create(UserApi::class.java)
        retro.getUserData(x_access_token).enqueue(object : Callback<UserResponse>{
            override fun onResponse(call: Call<UserResponse>?, response: Response<UserResponse>?) {
                if(response != null){
                    if(response.isSuccessful()){
                        if(response.code() == 200) {
                            user = response.body()
                            binding.hiUser.text = getString(R.string.hi_label, user.getFullname())
                            binding.license.text = getString(R.string.license, user.getLicense())
                        }
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

    private fun initHandler() {

        binding.logoutBtn.setOnClickListener {
            val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
            sharedPreferences.edit().clear().commit()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun _tickRefreshUserData() {
        userThread.launch {
            do {
                if (x_access_token != null && x_access_token != ""){
                    _loadUserData()
                }
                Log.d(TAG, "refreshed user data")
                delay(6000)
            } while (true)
        }
    }
}