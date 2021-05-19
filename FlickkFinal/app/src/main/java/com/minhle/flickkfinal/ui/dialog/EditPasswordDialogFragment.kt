package com.minhle.flickkfinal.ui.dialog


import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.minhle.flickkfinal.R
import com.minhle.flickkfinal.ui.splashscreen.SplashScreenActivity
import com.minhle.flickkfinal.utils.Constants
import com.minhle.flickkfinal.utils.SharePref
import com.minhle.flickkfinal.utils.get
import kotlinx.android.synthetic.main.dialog_edit.*
import kotlinx.android.synthetic.main.dialog_edit.btnSaveUsername
import kotlinx.android.synthetic.main.dialog_edit_password.*


class EditPasswordDialogFragment : DialogFragment() {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private var fireStore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var auth: FirebaseAuth
    private val dialogLoading by lazy {
        Dialog(requireContext())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        dialogLoading.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogLoading.setCancelable(true)
        dialogLoading.setContentView(R.layout.dialog_loading)


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog!!.window?.setBackgroundDrawableResource(R.drawable.round_corner);
        return inflater.inflate(R.layout.dialog_edit_password, container, false)

    }


    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)



        btnSaveUsername.setOnClickListener {
            changePassword()

        }


    }

    private fun changePassword() {

        if (edt_old_password.text.toString().trim().isNotEmpty() &&
            edt_new_password.text.toString().trim().isNotEmpty() &&
            edt_retype_pass.text.toString().trim().isNotEmpty()
        ) {
            if (edt_old_password.text.toString().trim().length < 6 || edt_old_password.text.toString().trim().length > 20) {
                Toast.makeText(context, R.string.invalid_pass_condition, Toast.LENGTH_SHORT).show()
                return
            } else if (edt_new_password.text.toString().trim().length < 6 || edt_new_password.text.toString().trim().length > 20) {
                Toast.makeText(context, R.string.invalid_pass_condition, Toast.LENGTH_SHORT).show()
                return
            } else if (edt_retype_pass.text.toString().trim().length < 6 || edt_retype_pass.text.toString().trim().length > 20) {
                Toast.makeText(context, R.string.invalid_pass_condition, Toast.LENGTH_SHORT).show()
                return
            } else if (edt_old_password.text.toString().trim() == edt_new_password.text.toString().trim()) {
                Toast.makeText(
                    context,
                    R.string.invalid_pass_duplicate,
                    Toast.LENGTH_SHORT
                ).show()
                return
            }
            if (edt_new_password.text.toString().trim() == edt_retype_pass.text.toString().trim()) {
                dialogLoading.show()
                val user = auth.currentUser
                if (user != null && user.email != null) {
                    val credential = EmailAuthProvider
                        .getCredential(user.email!!, edt_old_password.text.toString().trim())
                    user.reauthenticate(credential)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                user.updatePassword(edt_new_password.text.toString().trim())
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            fireStore.collection("Users").document(user.uid).update(
                                                "password",
                                                edt_new_password.text.toString().trim()
                                            ).addOnSuccessListener {}
                                            Toast.makeText(
                                                context,
                                                "Password changed successfully.",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            dialogLoading.dismiss()
                                            dismiss()
                                            /* auth.signOut()*/
                                        }
                                    }

                            } else {
                                Toast.makeText(
                                    context,
                                    "Re-Authentication failed.",
                                    Toast.LENGTH_SHORT
                                ).show()
                                dialogLoading.dismiss()
                            }
                        }
                } else {
                    activity?.finish()
                }

            } else {
                Toast.makeText(context, "Password mismatching.", Toast.LENGTH_SHORT).show()
            }

        } else {
            Toast.makeText(context, "Please enter all the fields.", Toast.LENGTH_SHORT).show()
        }

    }
}


