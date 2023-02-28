package com.example.myapplication

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


class registerStep03 : Fragment() {


    lateinit var imageBitmap:Bitmap
    private  val REQUEST_IMAGE_GALLERY_FRONT =321
    private  val REQUEST_IMAGE_CAMERA_FRONT=241
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_register_step03, container, false)

        val FrontSide = view.findViewById(R.id.imageView6) as ImageView
       // val BackSide = view.findViewById(R.id.imageView15) as ImageView
        val JoinAccount = view.findViewById(R.id.pin) as Button

        FrontSide.setOnClickListener {

            val builder = AlertDialog.Builder(context)
            builder.setTitle("Select Image")
            builder.setMessage("Choose your option?")
            builder.setPositiveButton("Gallery") { dialog, which ->
                dialog.dismiss()
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"

                startActivityForResult(intent, REQUEST_IMAGE_GALLERY_FRONT)
            }

            builder.setNegativeButton("Camera") { dialog, which ->
                dialog.dismiss()

                Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(Intent(MediaStore.ACTION_IMAGE_CAPTURE),
                    REQUEST_IMAGE_CAMERA_FRONT)

            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

        JoinAccount.setOnClickListener{

            var driverlicense1=driverlicense(null,"",null)
            // convert Bitmap to File
            val filesDir = requireActivity().getApplicationContext().getFilesDir()
            val file = File(filesDir, "image" + ".png")
            val bos = ByteArrayOutputStream()

            imageBitmap.compress(Bitmap.CompressFormat.PNG, 0, bos)
            val bitmapdata = bos.toByteArray()
            val fos = FileOutputStream(file)
            fos.write(bitmapdata)
            fos.flush()
            fos.close()
            val reqFile = RequestBody.create(MediaType.parse("image/*"), file)
            val image = MultipartBody.Part.createFormData("image", file.getName(), reqFile)
            val driverlicensebody =  MultipartBody.Part.createFormData("driverlicense", Gson().toJson(driverlicense1))
            addaccountStep03(image,driverlicensebody)

        }
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val FrontSide = view?.findViewById(R.id.imageView6) as ImageView
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_GALLERY_FRONT && resultCode == Activity.RESULT_OK && data!=null) {
            imageBitmap=data.data as Bitmap

            FrontSide.setImageURI(data.data)

        }
         if (requestCode == REQUEST_IMAGE_CAMERA_FRONT && resultCode == Activity.RESULT_OK && data!=null) {

             imageBitmap=data.extras?.get("data") as Bitmap

             FrontSide.setImageBitmap(imageBitmap)

        }

    }

    private fun addaccountStep03(body: MultipartBody.Part, driverlicense:MultipartBody.Part) {

        CoroutineScope(Dispatchers.IO).launch {
            val response = RetrofitService.endpoint.addPDL(body,driverlicense)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {

                    findNavController().navigate(R.id.action_registerStep032_to_homeFragment)
                    Toast.makeText(requireActivity(), "sucess", Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(requireActivity(), "une erreur", Toast.LENGTH_SHORT).show()
                }

            }



        }
    }
}