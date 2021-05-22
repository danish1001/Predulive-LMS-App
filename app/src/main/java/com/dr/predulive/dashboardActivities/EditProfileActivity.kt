package com.dr.predulive.dashboardActivities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.dr.predulive.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream
import java.util.*

class EditProfileActivity : AppCompatActivity() {

    private lateinit var name: EditText
    private lateinit var email: TextView
    private lateinit var dob: EditText
    private lateinit var about: EditText
    private lateinit var mAuth: FirebaseAuth

    private lateinit var who: String
    private lateinit var uid: String
    private lateinit var editProfileImageView: ImageView
    private lateinit var storage: FirebaseStorage
    private lateinit var imageName: String
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        mAuth = FirebaseAuth.getInstance()

        name = findViewById<View>(R.id.editProfileName) as EditText
        email = findViewById<View>(R.id.editProfileEmail) as TextView
        dob = findViewById<View>(R.id.editProfileDOB) as EditText
        about = findViewById<View>(R.id.editProfileAbout) as EditText
        editProfileImageView = findViewById<View>(R.id.editProfileImageView) as ImageView
        storage = Firebase.storage

        var intent: Intent = intent
        who = intent.getStringExtra("who").toString()
        uid = mAuth.uid.toString()
        imageName = "$uid.jpg"
        auth = Firebase.auth


        loadProfileData()

    }

    fun chooseImage(view: View?) {
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        } else {
            getPhoto()
        }
    }

    fun getPhoto() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val selectedImage = data!!.data
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            try {
                val bitmap = ImageDecoder.decodeBitmap(
                    ImageDecoder.createSource(
                        this.contentResolver,
                        selectedImage!!
                    )
                )
                editProfileImageView.setImageBitmap(bitmap)
                next(bitmap)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun next(bitmap: Bitmap) {
        // Get the data from an ImageView as bytes
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos)
        val data = baos.toByteArray()

        // Create a storage reference from our app
        val storageRef = storage.reference.child("images").child(imageName)
        val uploadTask = storageRef.putBytes(data)

        uploadTask.addOnFailureListener { // Handle unsuccessful uploads
            Toast.makeText(applicationContext, "Upload Unsuccessful", Toast.LENGTH_SHORT).show()
        }.addOnSuccessListener { taskSnapshot ->
            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
            // ...
            Toast.makeText(applicationContext, "Successful", Toast.LENGTH_SHORT).show()

            // getting download URL
            if (taskSnapshot.metadata != null) {
                if (taskSnapshot.metadata!!.reference != null) {
                    val result = taskSnapshot.storage.downloadUrl
                    result.addOnSuccessListener { uri ->
                        val imageUrl = uri.toString()

                        FirebaseDatabase.getInstance().reference
                            .child(uid).child("PROFILE_URL").setValue(imageUrl)

                    }
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getPhoto()
            }
        }
    }

    fun loadProfileData() {

        Log.i("uid is ", mAuth.uid.toString())
        Log.i("who is ", who.toString())

        FirebaseDatabase.getInstance().reference.child(uid).get().addOnSuccessListener {
            email.text = it.child("EMAIL").value.toString()
            name.setText(it.child("NAME").value.toString())
            dob.setText(it.child("DOB").value.toString())
            about.setText(it.child("ABOUT").value.toString())
        }.addOnFailureListener {
            Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show()
        }

        // loading profile image
        FirebaseDatabase.getInstance().reference
            .child(auth.uid.toString()).child("PROFILE_URL").get().addOnSuccessListener {

                Glide.with(this)
                        .load(it.value.toString())
                        .placeholder(R.drawable.loading_layout)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(editProfileImageView)

            }

    }

    fun saveProfile(view: View) {

        FirebaseDatabase.getInstance().reference
            .child(uid).child("DOB").setValue(dob.text.trim().toString())

        FirebaseDatabase.getInstance().reference
            .child(uid).child("NAME").setValue(name.text.trim().toString())

        FirebaseDatabase.getInstance().reference
            .child(uid).child("ABOUT").setValue(about.text.trim().toString())

        loadProfileData()
        Toast.makeText(this, "Successfully Saved !", Toast.LENGTH_SHORT).show()

    }
}