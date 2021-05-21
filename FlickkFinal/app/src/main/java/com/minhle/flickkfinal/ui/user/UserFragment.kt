package com.minhle.flickkfinal.ui.user

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.app.Dialog
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.minhle.flickkfinal.R
import com.minhle.flickkfinal.base.BaseFragment
import com.minhle.flickkfinal.databinding.FragmentUserBinding
import com.minhle.flickkfinal.ui.authentication.LoggedInViewModel
import com.minhle.flickkfinal.ui.dialog.EditNameDialogFragment
import com.minhle.flickkfinal.ui.dialog.EditPasswordDialogFragment
import com.minhle.flickkfinal.ui.splashscreen.SplashScreenActivity
import com.minhle.flickkfinal.utils.Constants.Companion.CAMERA_PICK
import com.minhle.flickkfinal.utils.Constants.Companion.GALLERY_PICK
import com.minhle.flickkfinal.utils.Constants.Companion.KEY_FINGER
import com.minhle.flickkfinal.utils.Constants.Companion.KEY_UID
import com.minhle.flickkfinal.utils.SharePref
import com.minhle.flickkfinal.utils.get
import com.minhle.flickkfinal.utils.put



@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "DEPRECATED_IDENTITY_EQUALS")
class UserFragment : BaseFragment(), EditNameDialogFragment.OnAddUsernameListener {
    private var imageUri: Uri? = null

    private var fireStore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private var loggedInViewModel: LoggedInViewModel? = null

    private val binding: FragmentUserBinding
        get() = (getViewBinding() as FragmentUserBinding)

    private val controller by lazy {
        findNavController()
    }

    private val dialogLoading by lazy {
        Dialog(requireContext())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)

    }

    override fun getLayoutId(): Int = R.layout.fragment_user


    @SuppressLint("UseCompatLoadingForDrawables")
    override fun initControls(view: View, savedInstanceState: Bundle?) {
        //setup dialog
        dialogLoading.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogLoading.setCancelable(false)
        dialogLoading.setContentView(R.layout.dialog_loading)

        getInfoUser()

        loggedInViewModel = ViewModelProvider(this).get(LoggedInViewModel::class.java)
        loggedInViewModel!!.userLiveData.observe(this,
            { firebaseUser ->
                if (firebaseUser != null) {
                    binding.tvEmail.text = firebaseUser.email
                    binding.btnLogout.isEnabled = true


                } else {
                    binding.btnLogout.isEnabled = false
                }
            })

        loggedInViewModel!!.loggedOutLiveData.observe(this, {
            if (it) {
                Toast.makeText(context, "User Logged Out", Toast.LENGTH_SHORT).show()
                val intent = Intent(context, SplashScreenActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
        })

        val check = SharePref.create(context)?.get(KEY_FINGER, false)
        binding.swFinger.isChecked = check!!

        checkFingerAuth()


    }


    override fun initEvents() {
        binding.toolbarProfile.setNavigationOnClickListener {
            controller.popBackStack()
        }
        binding.btnLogout.setOnClickListener {
            loggedInViewModel?.logOut()
        }

        binding.profileImage.setOnClickListener {
            showImagePickDialog()
        }


        binding.swFinger.setOnCheckedChangeListener { _, isChecked ->
            val userId: String = firebaseAuth.currentUser.uid
            fireStore.collection("Users").document(userId).update("fingerAuth", isChecked)
                .addOnSuccessListener {}
            SharePref.create(context)?.put(KEY_FINGER, isChecked)
            SharePref.create(context)?.put(KEY_UID, userId)
        }

        binding.tvUsername.setOnClickListener {
            val fm: FragmentManager = requireFragmentManager()
            val editDialog: DialogFragment = EditNameDialogFragment()
            editDialog.setTargetFragment(this, 0)
            editDialog.show(fm, "fragment_dialog")
        }
    
        binding.tvChangePass.setOnClickListener {
            val fm: FragmentManager = requireFragmentManager()
            val editDialog: DialogFragment = EditPasswordDialogFragment()
            editDialog.setTargetFragment(this, 0)
            editDialog.show(fm, "password_fragment_dialog")
        }
    }


    private fun showImagePickDialog() {
        val items = arrayOf("Camera", "Gallery")
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle("Choose an Option")
        builder.setItems(items) { _, i ->
            if (i == 0) {
                showDialogCamera()
            }
            if (i == 1) {
                showGalleryDialogue()
            }
        }
        builder.create().show()
    }




    private fun showGalleryDialogue() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, GALLERY_PICK)
    }

    private fun showDialogCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "Temp Pick")
        values.put(MediaStore.Images.Media.TITLE, "Temp Desc")
        imageUri =
            requireContext().contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                values
            )
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        startActivityForResult(intent, CAMERA_PICK)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === GALLERY_PICK && resultCode === RESULT_OK) {

            val uri = data?.data!!
            dialogLoading.show()
            val timestamp = System.currentTimeMillis().toString()
            val path = "Photos/photos_$timestamp"
            val storageReference = FirebaseStorage.getInstance().getReference(path)
            storageReference.putFile(uri).addOnSuccessListener { taskSnapshot ->
                val task: Task<Uri> = taskSnapshot.metadata!!.reference!!.downloadUrl
                task.addOnSuccessListener { uri ->
                    dialogLoading.dismiss()
                    val photoId = uri.toString()
                    imageUri = uri
                    context?.let {
                        Glide.with(it).load(imageUri).centerCrop()
                            .thumbnail(0.1f)
                            .into(binding.profileImage)

                    }
                    val user = FirebaseAuth.getInstance().currentUser
                    val userId = user.uid
                    fireStore.collection("Users").document(userId).update("imageUrl", photoId)
                        .addOnSuccessListener {}
                }.addOnFailureListener {
                    dialogLoading.dismiss()
                    Toast.makeText(context,"Update failure",Toast.LENGTH_SHORT).show()
                }
            }
        }


        if (requestCode === CAMERA_PICK && resultCode === RESULT_OK) {
            val uri = imageUri!!
            dialogLoading.show()
            val timestamp = System.currentTimeMillis().toString()
            val path = "Photos/photos_$timestamp"
            val ref = FirebaseStorage.getInstance().getReference(path)
            ref.putFile(uri).addOnSuccessListener { taskSnapshot ->
                val task: Task<Uri> = taskSnapshot.metadata!!.reference!!.downloadUrl
                task.addOnSuccessListener { uri ->
                    dialogLoading.dismiss()
                    val photoId = uri.toString()
                    val user = FirebaseAuth.getInstance().currentUser
                    val userId = user.uid
                    imageUri = uri

                    context?.let {
                        Glide.with(it).load(imageUri).centerCrop()
                            .thumbnail(0.1f)
                            .into(binding.profileImage)
                    }
                    fireStore.collection("Users").document(userId).update("imageUrl", photoId)
                        .addOnSuccessListener {}
                }.addOnFailureListener {
                    dialogLoading.dismiss()
                    Toast.makeText(context,"Update failure",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    /* @SuppressLint("UseCompatLoadingForDrawables")
     override fun onActivityCreated(savedInstanceState: Bundle?) {
         super.onActivityCreated(savedInstanceState)
         getInfoUser()
     }
 */




    private fun getInfoUser() {
        val userId: String = firebaseAuth.currentUser.uid

        fireStore.collection("Users").document(userId).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userModel: DocumentSnapshot? = task.result
                    val name = userModel?.getString("username")
                    val imageUrl = userModel?.getString("imageUrl")
                    binding.tvUsername.text = name
                    if (imageUrl != "default") {
                        Glide.with(requireContext())
                            .load(imageUrl)
                            .centerCrop()
                            .error(R.drawable.ic_user)
                            .into(binding.profileImage)
                    } else {
                        binding.profileImage.setImageDrawable(resources.getDrawable(R.drawable.ic_user))
                    }

                } else {
                    Toast.makeText(context, R.string.default_error_msg, Toast.LENGTH_SHORT).show()
                }
            }
    }


    private fun checkFingerAuth() {

        val userId: String = firebaseAuth.currentUser.uid
        fireStore.collection("Users").document(userId).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userModel: DocumentSnapshot? = task.result
                    val checkFinger = userModel?.getBoolean("fingerAuth")
                    binding.swFinger.isChecked = checkFinger!!


                }
            }
    }


    override fun onAddUsername(username: String?) {
        val user = FirebaseAuth.getInstance().currentUser
        val userId = user.uid
        fireStore.collection("Users").document(userId).update("username", username)
            .addOnSuccessListener {}
        getInfoUser()
    }


}