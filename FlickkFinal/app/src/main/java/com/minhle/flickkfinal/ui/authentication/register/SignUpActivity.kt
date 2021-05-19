package com.minhle.flickkfinal.ui.authentication.register

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
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.minhle.flickkfinal.MainActivity
import com.minhle.flickkfinal.R
import com.minhle.flickkfinal.base.BaseActivity
import com.minhle.flickkfinal.databinding.ActivitySignupBinding
import com.minhle.flickkfinal.ui.authentication.LoginRegisterViewModel
import com.minhle.flickkfinal.ui.authentication.login.LoginActivity
import java.util.regex.Pattern


class SignUpActivity : BaseActivity() {
    private var loginRegisterViewModel: LoginRegisterViewModel? = null

    private val binding: ActivitySignupBinding
        get() = (getViewBinding() as ActivitySignupBinding)

    private val dialogLoading by lazy {
        Dialog(this)
    }
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private var fireStore: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun getLayoutId(): Int = R.layout.activity_signup

    @RequiresApi(Build.VERSION_CODES.M)
    override fun initControls(savedInstanceState: Bundle?) {

        if (Build.VERSION.SDK_INT >= 21) {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = this.resources.getColor(R.color.white)
        }
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        dialogLoading.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogLoading.setCancelable(true)
        dialogLoading.setContentView(R.layout.dialog_loading)


        loginRegisterViewModel = ViewModelProvider(this).get(
            LoginRegisterViewModel::class.java
        )
               loginRegisterViewModel!!.userLiveData.observe(this,
                   { firebaseUser ->
                       if (firebaseUser != null) {
                           val intent = Intent(this, MainActivity::class.java)
                           startActivity(intent)
                           dialogLoading.dismiss()
                           Toast.makeText(this, "Register Success!", Toast.LENGTH_SHORT).show()

                       }
                   })
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun initEvents() {
        binding.btnBackToLogin.setOnClickListener {
            finish()
        }

        binding.btnRegister.setOnClickListener {
            register()
        }

        onTextChanged()
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun register() {

        var validUser = true

        if (binding.edtEmail.text.toString().trim().isEmpty()) {
            binding.textInputEmail.error = resources.getString(R.string.empty_error)
            validUser = false
        } else if (!isValidEmail(binding.edtEmail.text.toString().trim())) {
            binding.textInputEmail.error = resources.getString(R.string.invalid_email)
            validUser = false
        }

        if (binding.edtName.text.toString().trim().isEmpty()) {
            binding.textInputName.error = resources.getString(R.string.empty_error)
            validUser = false
        } else if (binding.edtName.text.toString().trim().length < 4 || binding.edtName.text.toString().trim().length > 20) {
            binding.textInputName.error = resources.getString(R.string.invalid_name_condition)
            validUser = false
        }else if (!isUsername(binding.edtName.text.toString().trim())) {
            binding.textInputName.error = resources.getString(R.string.invalid_character)
            validUser = false
        }

        if (binding.edtPassword.text.toString().trim().isEmpty()) {
            binding.textInputPassword.error = resources.getString(R.string.empty_error)
            validUser = false
        } else if (binding.edtPassword.text.toString().trim().length < 8 || binding.edtPassword.text.toString().trim().length > 20) {
            binding.textInputPassword.error = resources.getString(R.string.invalid_pass_condition)
            validUser = false
        }

        if (binding.edtRePassword.text.toString().trim().isEmpty()) {
            binding.textInputRetypePass.error = resources.getString(R.string.empty_error)
            validUser = false
        } else if (binding.edtPassword.text.toString().trim() != binding.edtRePassword.text.toString().trim()) {
            binding.textInputRetypePass.error = resources.getString(R.string.not_match_pass)
            validUser = false
        }

        if (validUser) {
            loginRegisterViewModel?.register(
                binding.edtName.text.toString().trim(),
                binding.edtEmail.text.toString().trim(),
                binding.edtPassword.text.toString().trim()
            )

            dialogLoading.show()
        }
    }


    private fun onTextChanged() {
        binding.edtEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (binding.edtEmail.text.toString().trim().isNotEmpty()) {
                    binding.textInputEmail.error = null
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
        binding.edtName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (binding.edtName.text.toString().trim().isNotEmpty()) {
                    binding.textInputName.error = null
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
        binding.edtPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (binding.edtPassword.text.toString().trim().isNotEmpty()) {
                    binding.textInputPassword.error = null
                    binding.textInputPassword.boxStrokeColor =
                        checkStrengthPass(binding.edtPassword.text.toString().trim())
                } else
                    binding.textInputPassword.boxStrokeColor =
                        resources.getColor(R.color.colorPrimaryDark)
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        binding.edtRePassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (binding.edtRePassword.text.toString().trim().isNotEmpty()) {
                    binding.textInputRetypePass.error = null
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
        binding.edtPassword.onFocusChangeListener =
            View.OnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    hideKeyboard(v)
                }
            }
    }

    private fun checkStrengthPass(pass: String): Int {
        val chars = pass.toCharArray()
        var containDigit: Boolean = false
        var containUpperCase: Boolean = false
        var containLowerCase: Boolean = false

        for (i in chars.indices) {
            if (chars[i].isDigit()) containDigit = true
            if (chars[i].isUpperCase()) containUpperCase = true
            if (chars[i].isLowerCase()) containLowerCase = true
        }

        return if (pass.length in 8..20 && !containDigit && containUpperCase && containLowerCase) {
            resources.getColor(R.color.medium_pass_color)
        } else if (pass.length in 8..20 && containDigit && containUpperCase && containLowerCase) {
            resources.getColor(R.color.strong_pass_color)
        } else {
            resources.getColor(R.color.weak_pass_color)
        }
    }

    private fun isValidEmail(target: CharSequence?): Boolean {
        return if (target == null) false
        else Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    private fun isUsername(target: CharSequence?): Boolean {
        return if (target == null) false
        else USERNAME_PATTERN.matcher(target).matches()
    }

    val USERNAME_PATTERN = Pattern.compile(
        "^[A-Za-z]\\w{3,19}$"
    )

    private fun hideKeyboard(view: View) {
        val inputMethodManager: InputMethodManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }



}
