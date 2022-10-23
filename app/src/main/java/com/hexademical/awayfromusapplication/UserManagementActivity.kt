package com.hexademical.awayfromusapplication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hexademical.awayfromusapplication.API.ResetRespone
import com.hexademical.awayfromusapplication.API.UserResponse
import com.hexademical.awayfromusapplication.Interface.UserApi
import com.hexademical.awayfromusapplication.ResourceRecyclerView.ResourceAdapter
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

    // @ Recycler View
    private lateinit var resourceRecyclerView: RecyclerView

    // @ debug tag
    private var TAG = "UserManagementActivity"

    // @ Coroutine for refreshing user data
    private val userThread = CoroutineScope(CoroutineName("userDataThread"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // binding inflate
        binding = ActivityUserManagementBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // @ RecyclerView Init
        resourceRecyclerView = binding.resources
        resourceRecyclerView.layoutManager = LinearLayoutManager(this)
        resourceRecyclerView.setHasFixedSize(true)

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
                            resourceRecyclerView.adapter = user.getResoures()
                                ?.let { ResourceAdapter(it) }
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

    private fun _tickRefreshUserData() {
        userThread.launch {
            do {
                if (x_access_token != null && x_access_token != ""){
                    _loadUserData()
                }
                Log.d(TAG, "refreshed user data")
                delay(10000)
            } while (true)
        }
    }

    private fun initHandler() {
        binding.logoutBtn.setOnClickListener {
            animationImageButton(binding.logoutBtn)
            val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
            sharedPreferences.edit().clear().commit()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.resetIpBtn.setOnClickListener {
            animationButton(binding.resetIpBtn)
            val retro = Retro().getRetroClientInstance().create(UserApi::class.java)
            retro.resetIP(x_access_token).enqueue(object : Callback<ResetRespone>{
                override fun onResponse(call: Call<ResetRespone>?, response: Response<ResetRespone>?) {
                    if(response != null){
                        Log.d(TAG, "reset-btn/response code: ${response.code()}")
                        if(response.isSuccessful()){
                            if(response.code() == 200) {
                                val res = response.body()
                                Log.d(TAG, "msg: ${res.msg}")
                                Toast.makeText(applicationContext, res.msg, Toast.LENGTH_SHORT).show()
                                _loadUserData()
                            }
                        }else if(response?.code() == 429) {
                            val animation: Animation = AlphaAnimation(1.0f, 0.0f)
                            animation.duration = 300
                            binding.limitRateLabel.startAnimation(animation)
                        }
                    }
                }

                override fun onFailure(call: Call<ResetRespone>?, t: Throwable?) {
                    Log.e(TAG, "error: ${t?.message}")
                }
            })
        }
    }

    private fun animationButton(button: Button) {
        val anim: Animation = AlphaAnimation(1.0f, 0.5f)
        anim.duration = 100
        button.startAnimation(anim)
    }

    private fun animationImageButton(button: ImageButton) {
        val anim: Animation = AlphaAnimation(1.0f, 0.5f)
        anim.duration = 100
        button.startAnimation(anim)
    }


}