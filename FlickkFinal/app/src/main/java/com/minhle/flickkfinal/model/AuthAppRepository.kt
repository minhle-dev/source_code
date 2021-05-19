package com.minhle.flickkfinal.model

import android.app.Application
import android.app.Dialog
import android.os.Build
import android.view.Window
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.minhle.flickkfinal.R
import com.minhle.flickkfinal.application.FlickkApplication


class AuthAppRepository(private val application: Application) {
    private var fireStore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val userLiveData: MutableLiveData<FirebaseUser> = MutableLiveData()
    val loggedOutLiveData: MutableLiveData<Boolean> = MutableLiveData()


    @RequiresApi(Build.VERSION_CODES.P)
    fun login(email: String?, password: String?) {

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(application.mainExecutor,
                { task ->
                    if (task.isSuccessful) {
                        userLiveData.postValue(firebaseAuth.currentUser)
                        Toast.makeText(
                            application.applicationContext,
                            "Login success! " ,
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            application.applicationContext,
                            "Login Failure: " + task.exception!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
    }


    @RequiresApi(Build.VERSION_CODES.P)
    fun register(name: String,email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(application.mainExecutor,
                { task ->
                    if (task.isSuccessful) {
                        userLiveData.postValue(firebaseAuth.currentUser)
                        val userId = firebaseAuth.currentUser?.uid as String
                        val hashMap: HashMap<String, Any> = HashMap()
                        hashMap["userId"] = userId
                        hashMap["imageUrl"] = "default"
                        hashMap["email"] = email
                        hashMap["password"] = password
                        hashMap["username"] = name
                        hashMap["fingerAuth"] = false
                        fireStore.collection("Users").document(userId).set(hashMap)
                            .addOnCompleteListener { }
                    } else {
                        Toast.makeText(
                            application.applicationContext,
                            "Registration Failure: " + task.exception!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
    }

    fun logOut() {
        firebaseAuth.signOut()
        loggedOutLiveData.postValue(true)
    }

    init {

        if (firebaseAuth.currentUser != null) {
            userLiveData.postValue(firebaseAuth.currentUser)
            loggedOutLiveData.postValue(false)
        }

    }
}