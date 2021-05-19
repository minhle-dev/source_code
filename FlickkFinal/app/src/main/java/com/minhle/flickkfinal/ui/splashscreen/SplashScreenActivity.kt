package com.minhle.flickkfinal.ui.splashscreen

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.minhle.flickkfinal.MainActivity
import com.minhle.flickkfinal.R
import com.minhle.flickkfinal.base.BaseActivity
import com.minhle.flickkfinal.databinding.ActivitySplashScreenBinding
import com.minhle.flickkfinal.ui.authentication.login.LoginActivity
import com.minhle.flickkfinal.utils.isConnected


class SplashScreenActivity : BaseActivity() {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val userLiveData: MutableLiveData<FirebaseUser> = MutableLiveData()
    val loggedOutLiveData: MutableLiveData<Boolean> = MutableLiveData()
    private val binding: ActivitySplashScreenBinding
        get() = (getViewBinding() as ActivitySplashScreenBinding)

    override fun getLayoutId(): Int = R.layout.activity_splash_screen

    @RequiresApi(Build.VERSION_CODES.M)
    override fun initControls(savedInstanceState: Bundle?) {

        if (Build.VERSION.SDK_INT >= 21) {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = this.resources.getColor(R.color.white)
        }
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        val logoAnimation = AnimationUtils.loadAnimation(this, R.anim.logo_animation)

        binding.appLogo.startAnimation(logoAnimation)

        val homeIntent = Intent(this, MainActivity::class.java)
        val intentLogin = Intent(this, LoginActivity::class.java)
        if (this.isConnected) {
            val thread: Thread = object : Thread() {
                override fun run() {
                    super.run()
                    try {
                        sleep(3000)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    } finally {
                        if (firebaseAuth.currentUser != null) {
                            userLiveData.postValue(firebaseAuth.currentUser)
                            loggedOutLiveData.postValue(false)

                            startActivity(homeIntent)
                            finish()
                        } else {
                            startActivity(intentLogin)
                            finish()
                        }
                        finish()
                    }
                }
            }
            thread.start()
        } else {
            Toast.makeText(this, "No connect internet", Toast.LENGTH_LONG).show()

        }
    }

    override fun initEvents() {

    }



}

