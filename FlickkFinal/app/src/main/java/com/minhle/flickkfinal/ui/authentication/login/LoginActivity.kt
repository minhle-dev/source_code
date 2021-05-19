package com.minhle.flickkfinal.ui.authentication.login


import android.app.Dialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.minhle.flickkfinal.MainActivity
import com.minhle.flickkfinal.R
import com.minhle.flickkfinal.base.BaseActivity
import com.minhle.flickkfinal.databinding.ActivityLoginBinding
import com.minhle.flickkfinal.ui.authentication.LoginRegisterViewModel
import com.minhle.flickkfinal.ui.authentication.register.SignUpActivity
import com.minhle.flickkfinal.utils.Constants.Companion.KEY_FINGER
import com.minhle.flickkfinal.utils.Constants.Companion.KEY_UID
import com.minhle.flickkfinal.utils.SharePref
import com.minhle.flickkfinal.utils.get
import java.util.concurrent.Executor
import java.util.regex.Pattern


class LoginActivity : BaseActivity() {
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var biometricManager: BiometricManager

    private var fireStore: FirebaseFirestore = FirebaseFirestore.getInstance()

    private var loginRegisterViewModel: LoginRegisterViewModel? = null
    private val binding: ActivityLoginBinding
        get() = (getViewBinding() as ActivityLoginBinding)

    private val dialogLoading by lazy {
        Dialog(this)
    }

    override fun getLayoutId(): Int = R.layout.activity_login

    @RequiresApi(Build.VERSION_CODES.M)
    override fun initControls(savedInstanceState: Bundle?) {

        if (Build.VERSION.SDK_INT >= 21) {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = this.resources.getColor(R.color.white)
        }
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        val userId = SharePref.create(this)!!.get(KEY_UID, "") as String
        //setup biometric
        executor = ContextCompat.getMainExecutor(this)
        biometricManager = BiometricManager.from(this)
        //setup dialog
        dialogLoading.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogLoading.setCancelable(false)
        dialogLoading.setContentView(R.layout.dialog_loading)

        loginRegisterViewModel = ViewModelProvider(this).get(
            LoginRegisterViewModel::class.java
        )

        loginRegisterViewModel!!.userLiveData.observe(this,
            { firebaseUser ->
                if (firebaseUser != null) {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            })

        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                @RequiresApi(Build.VERSION_CODES.P)
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)

                    fireStore.collection("Users").document(userId).get()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                dialogLoading.show()

                                val userModel: DocumentSnapshot? = task.result
                                val email = userModel?.getString("email")
                                val password = userModel?.getString("password")

                                loginRegisterViewModel?.login(
                                    email.toString(),
                                    password.toString()
                                )
                            }
                        }
                }

                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence
                ) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(
                        applicationContext,
                        "Authentication error: $errString", Toast.LENGTH_SHORT
                    )
                        .show()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(
                        applicationContext, "Authentication failed",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            })



        checkViewFinger()
    }


    @RequiresApi(Build.VERSION_CODES.P)
    override fun initEvents() {
        binding.btnRegister.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.btnSignIn.setOnClickListener {

            login()
        }

        onTextChanged()

        binding.ivFinger.setOnClickListener {
            if (isBiometricFeatureAvailable()) {
                biometricPrompt.authenticate(buildBiometricPrompt())

            }

        }


    }

    private fun checkViewFinger() {
        val isCheckFinger = SharePref.create(this)?.get(KEY_FINGER, false) as Boolean

        binding.ivFinger.isVisible = isCheckFinger

    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun login() {
        var validEmail = true
        var validPass = true

        if (binding.edtEmail.text.toString().trim().isEmpty()) {
            binding.textInputEmail.error = resources.getString(R.string.empty_error)
            validEmail = false
        } else if (!isValidEmail(binding.edtEmail.text.toString().trim())) {
            binding.textInputEmail.error = resources.getString(R.string.invalid_email)
            validEmail = false
        }

        if (binding.edtPassword.text.toString().trim().isEmpty()) {
            binding.textInputPassword.error = resources.getString(R.string.empty_error)
            validPass = false
        } else if (binding.edtPassword.text.toString()
                .trim().length < 8 || binding.edtPassword.text.toString().trim().length > 20
        ) {
            binding.textInputPassword.error = resources.getString(R.string.invalid_pass_condition)
            validPass = false
        }
        if (validEmail && validPass) {
            loginRegisterViewModel?.login(
                binding.edtEmail.text.toString().trim(),
                binding.edtPassword.text.toString().trim(),
            )
            dialogLoading.show()
            val thread: Thread = object : Thread() {
                override fun run() {
                    super.run()
                    try {
                        sleep(1000)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    } finally {
                        dialogLoading.dismiss()
                    }
                }
            }
            thread.start()
        }


    }

    private fun onTextChanged() {

        binding.edtEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (binding.edtEmail.text.toString().isNotEmpty()) {
                    binding.textInputEmail.error = null
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        binding.edtPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (binding.edtPassword.text.toString().isNotEmpty()) {
                    binding.textInputPassword.error = null
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
        binding.edtEmail.onFocusChangeListener =
            View.OnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    hideKeyboard(v)
                }
            }
        binding.edtPassword.onFocusChangeListener =
            View.OnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    hideKeyboard(v)
                }
            }
    }

    private fun isValidEmail(target: CharSequence?): Boolean {
        return if (target == null) false
        else Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    private fun hideKeyboard(view: View) {
        val inputMethodManager: InputMethodManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun buildBiometricPrompt(): BiometricPrompt.PromptInfo {
        return BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login for my app")
            .setSubtitle("Login using your biometric credential")
            .setNegativeButtonText("Use account password")
            .setConfirmationRequired(false)
            .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.BIOMETRIC_WEAK)
            .build()
    }

    private fun isBiometricFeatureAvailable(): Boolean {
        return when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.BIOMETRIC_WEAK)) {
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                Toast.makeText(
                    this,
                    "Sorry. It seems your device has no biometric hardware",
                    Toast.LENGTH_SHORT
                ).show()
                false
            }
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                Toast.makeText(
                    this,
                    "Biometric features are currently unavailable.",
                    Toast.LENGTH_SHORT
                ).show()
                false
            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                Toast.makeText(
                    this,
                    "You have not registered any biometric credentials",
                    Toast.LENGTH_SHORT
                ).show()
                false
            }
            BiometricManager.BIOMETRIC_SUCCESS -> {
                true
            }
            else -> false
        }
    }


}