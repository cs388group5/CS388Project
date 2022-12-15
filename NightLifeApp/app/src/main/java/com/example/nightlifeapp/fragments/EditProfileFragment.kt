package com.example.nightlifeapp.fragments

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.example.nightlifeapp.R
import com.example.nightlifeapp.User
import com.parse.ParseException
import com.parse.ParseFile
import com.parse.ParseUser
import com.parse.SaveCallback
import java.io.File
import java.io.IOException


class EditProfileFragment : Fragment() {
    lateinit var user: MutableList<ParseUser>
    val photoFileName = "photo.jpg"
    var photoFile: File? = null
    val CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034
    private val PICK_PHOTO_CODE = 1064

    lateinit var btAddPhoto: Button
    lateinit var btSubmit: Button
    lateinit var btCancel: Button
    lateinit var etFirstName: EditText
    lateinit var etLastName: EditText
    lateinit var etEmail: EditText
    lateinit var ivProfile: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        etFirstName = view.findViewById(R.id.etFirstName)
        etLastName = view.findViewById(R.id.etLastName)
        etEmail = view.findViewById(R.id.etEmail)
        ivProfile = view.findViewById(R.id.ivAvatar)
        btSubmit = view.findViewById(R.id.btSubmit)
        btAddPhoto = view.findViewById(R.id.btPhoto)
        btCancel = view.findViewById(R.id.btCancel)

        var map = mutableMapOf<String,String>()
        var error = false
        user = queryUser(ParseUser.getCurrentUser())
        val userH : User = user.get(0) as User

        etFirstName.setText(userH.getFirstName())
        etLastName.setText(userH.getLastName())
        etEmail.setText(userH.email)
        btAddPhoto.setOnClickListener {
            onLaunchCamera()
        }
        btSubmit.setOnClickListener {
            if (etFirstName.text.toString().isEmpty()){
                error = true
            }
            if (etLastName.text.toString().isEmpty()){
                error = true
            }
            if (etEmail.text.toString().isEmpty()){
                error = true
            }
            if (photoFile == null){
                error = true
            }
            if (!error){
                map["firstname"] = etFirstName.text.toString()
                map["lastname"] = etLastName.text.toString()
                map["email"] = etEmail.text.toString()
                updateInfo(map, photoFile)

            } else {
                Toast.makeText(requireContext(),"One of the input entries is missing", Toast.LENGTH_SHORT);
            }
        }

        btCancel.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.flContainer, ProfileFragment()).commit()
        }
    }

    companion object {
        const val TAG = "EditProfileFragment"
    }

    // A map of parameters
    fun updateInfo(map: MutableMap<String, String>, photo: File?) {

        val contact : User= ParseUser.getCurrentUser() as User


        map["firstname"]?.let { contact.setFirstName(it) }
        map["lastname"]?.let { contact.setLastName(it) }
        map["email"]?.let { contact.email = it }
        contact.setProfilePicture(ParseFile(photo))
        contact.saveInBackground{
                exception ->
                if(exception != null){
                    exception.stackTrace
                    Log.e(TAG,"Error while saving Contact")
                    Log.e(TAG,"$exception")
//                    Toast.makeText(requireContext(), "Error saving the information", Toast.LENGTH_SHORT).show()
                } else {
                    Log.i(TAG,"Contact saved successfully")
                    Toast.makeText(requireContext(), "Successfully saved profile information", Toast.LENGTH_SHORT).show()
                    parentFragmentManager.beginTransaction().replace(R.id.flContainer, ProfileFragment()).commit()
                }

        }
    }


    fun onLaunchCamera() {
        // create Intent to take a picture and return control to the calling application
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // Create a File reference for future access
        photoFile = getPhotoFileUri(photoFileName)

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        if (photoFile != null) {
            val fileProvider: Uri =
                FileProvider.getUriForFile(requireContext(), "com.example.fileprovider", photoFile!!)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)

            // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
            // So as long as the result is not null, it's safe to use the intent.

            // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
            // So as long as the result is not null, it's safe to use the intent.
            if (intent.resolveActivity(requireContext().packageManager) != null) {
                // Start the image capture intent to take photo
                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE)
            }
        }
    }

    // Returns the File for a photo stored on disk given the fileName
    fun getPhotoFileUri(fileName: String): File {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        val mediaStorageDir =
            File(this.requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG)

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            Log.d(TAG, "failed to create directory")
        }

        // Return the file target for the photo based on filename
        return File(mediaStorageDir.path + File.separator + fileName)
    }

    private fun loadFromUri(photoUri: Uri?): Bitmap {
        var image: Bitmap? = null
        try {
            // check version of Android on device
            image = if (Build.VERSION.SDK_INT > 27) {
                // on newer versions of Android, use the new decodeBitmap method
                val source =
                    ImageDecoder.createSource(
                        this.requireContext().getContentResolver(),
                        photoUri!!)

                source?.let { ImageDecoder.decodeBitmap(it) }
            } else {
                // support older versions of Android by using getBitmap
                MediaStore.Images.Media.getBitmap(this.requireContext().contentResolver, photoUri)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return image!!
    }

    // Trigger gallery selection for a photo
    fun onPickPhoto() {
        // Create intent for picking a photo from the gallery
        val intent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(requireContext().packageManager) != null) {
            // Bring up gallery to select a photo
            startActivityForResult(intent, PICK_PHOTO_CODE)
        } else{
            Log.i(TAG,"Error  ${requireContext()}")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == AppCompatActivity.RESULT_OK) {
                // by this point we have the camera photo on disk
                val takenImage = BitmapFactory.decodeFile(photoFile!!.absolutePath)
                // RESIZE BITMAP, see section below
                // Load the taken image into a preview
                ivProfile.setImageBitmap(takenImage)
            } else { // Result was a failure
                Toast.makeText(requireContext(), "Error taking picture", Toast.LENGTH_SHORT).show()
            }
        }
        else if((data != null) && requestCode == PICK_PHOTO_CODE){
            val photoUri: Uri? = data.data

            // Load the image located at photoUri into selectedImage

            // Load the image located at photoUri into selectedImage
            val selectedImage: Bitmap = loadFromUri(photoUri)

            // Load the selected image into a preview

            // Load the selected image into a preview
            ivProfile.setImageBitmap(selectedImage)
        }
    }
    fun queryUser(user: ParseUser): MutableList<ParseUser> {
        //specify which class to query
        val query= ParseUser.getQuery()

        //find User
        query.whereEqualTo("username",user.username)
        query.addDescendingOrder("createdAt")
        query.setLimit(20)
        return query.find()
    }
}