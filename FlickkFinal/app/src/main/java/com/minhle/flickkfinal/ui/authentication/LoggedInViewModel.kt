package com.minhle.flickkfinal.ui.authentication

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.minhle.flickkfinal.application.FlickkApplication
import com.minhle.flickkfinal.model.AuthAppRepository


class LoggedInViewModel(application: Application) : AndroidViewModel(application) {
    private val authAppRepository: AuthAppRepository = AuthAppRepository(application)
    val userLiveData: MutableLiveData<FirebaseUser> = authAppRepository.userLiveData
    val loggedOutLiveData: MutableLiveData<Boolean> = authAppRepository.loggedOutLiveData

    fun logOut() {
        authAppRepository.logOut()
    }



}