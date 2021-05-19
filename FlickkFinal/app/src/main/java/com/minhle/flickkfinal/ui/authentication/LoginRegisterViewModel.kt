package com.minhle.flickkfinal.ui.authentication

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.minhle.flickkfinal.application.FlickkApplication
import com.minhle.flickkfinal.model.AuthAppRepository


class LoginRegisterViewModel(application: Application) :
    AndroidViewModel(application) {
    private val authAppRepository: AuthAppRepository = AuthAppRepository(application)
    val userLiveData: MutableLiveData<FirebaseUser> = authAppRepository.userLiveData

    @RequiresApi(Build.VERSION_CODES.P)
    fun login(email: String?, password: String?) {
        authAppRepository.login(email, password)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun register(name:String,email: String, password: String) {
        authAppRepository.register(name,email,password)
    }

}