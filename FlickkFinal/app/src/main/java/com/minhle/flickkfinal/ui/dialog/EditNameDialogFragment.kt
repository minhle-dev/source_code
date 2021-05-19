package com.minhle.flickkfinal.ui.dialog


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.minhle.flickkfinal.R
import kotlinx.android.synthetic.main.dialog_edit.*


class EditNameDialogFragment : DialogFragment() {
    interface OnAddUsernameListener {
        fun onAddUsername(username: String?)
    }

    private var callback: OnAddUsernameListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            callback = targetFragment as OnAddUsernameListener?
        } catch (e: ClassCastException) {
            throw ClassCastException("Calling Fragment must implement OnAddFriendListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog!!.window?.setBackgroundDrawableResource(R.drawable.round_corner);
        return inflater.inflate(R.layout.dialog_edit, container, false)

    }


    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)



        btnSaveUsername.setOnClickListener {
            editUsername()
        }


    }

    private fun editUsername() {

        if (edt_edit_name.text.toString().trim().isEmpty()) {
            Toast.makeText(context, "Username is not empty", Toast.LENGTH_SHORT).show()
        } else if (edt_edit_name.text.toString().trim().length < 4 || edt_edit_name.text.toString().trim().length > 20) {
            Toast.makeText(context, R.string.invalid_name_condition, Toast.LENGTH_SHORT).show()
        } else {
            callback?.onAddUsername(edt_edit_name.text.toString().trim())
            dismiss()
        }


    }


}

