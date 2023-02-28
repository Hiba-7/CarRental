package com.example.myapplication

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
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

@Suppress("NAME_SHADOWING")
@SuppressLint("CheckResult")
class registerStepp01 : Fragment() {

    lateinit var imageBitmap:Bitmap

    lateinit var disppic: ImageView
    private  val REQUEST_IMAGE_GALLERY =123
    private  val REQUEST_IMAGE_CAMERA=142
  override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,

    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_register_stepp01, container, false)
      var FullName = view.findViewById(R.id.editTextTextPersonName) as EditText
      var buttonSignin = view.findViewById(R.id.pin) as Button
        var password = view.findViewById(R.id.editTextTextPassword2) as EditText
        var Confirmpassword = view.findViewById(R.id.editTextTextPassword3) as EditText
      var PhoneNumber = view.findViewById(R.id.editTextPhone4) as EditText

        //Full Name validation
        val nameStream= RxTextView.textChanges(FullName)
            .skipInitialValue()
            .map { name ->
                name.isEmpty()
            }
        nameStream.subscribe{
            ShowNameExistAlert(it)
        }

      //Password validation
      val PasswordStream= RxTextView.textChanges(password)
          .skipInitialValue()
          .map { password ->
              ( ( password.length < 8) || (password.toString().equals("password")))
          }

      PasswordStream.subscribe{
          showTextMinimalAlert(it)
      }
      //Phone validation
      val PhoneStream= RxTextView.textChanges(PhoneNumber)
          .skipInitialValue()
          .map { PhoneNumber ->
              ((PhoneNumber.startsWith("0")==false)  || (PhoneNumber.length != 10) )
          }
      PhoneStream.subscribe{
          showPhoneNumberExistAlert(it)
      }


      //confirm password
      val passwordConfirmStream=Observable.merge(
          RxTextView.textChanges(Confirmpassword)
              .skipInitialValue()
              .map { password ->
                  password.toString()!=Confirmpassword.text.toString()
              },
          RxTextView.textChanges(Confirmpassword)
              .skipInitialValue()
              .map { confirmpassword ->
                  confirmpassword.toString()!=password.text.toString()
              }
      )
      passwordConfirmStream.subscribe{
          showPasswordConfirmAlert(it)
      }
      //Button Enable True or False
      val invalidFieldStream=Observable.combineLatest(
          nameStream,
          passwordConfirmStream,
          PasswordStream,

          PhoneStream,

          {
              nameInvalid:Boolean,PhoneInvalid:Boolean,PasswordInvalid:Boolean,ConfirmPassInvalid:Boolean ->
              !nameInvalid && !PasswordInvalid && !ConfirmPassInvalid && !PhoneInvalid
          })
      invalidFieldStream.subscribe{
          isValid->
          if(isValid){
              buttonSignin.isEnabled=true
          }
          else{
              buttonSignin.isEnabled=false
          }
      }



      buttonSignin.setOnClickListener {

          var user1 = user(null,PhoneNumber.text.toString(),password.text.toString(), FullName.text.toString(), "");

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
          val userBody =  MultipartBody.Part.createFormData("user", Gson().toJson(user1))
          addaccountStep01(image,userBody)

       }
         disppic = view.findViewById(R.id.displaypicture) as ImageView
        disppic.setOnClickListener{
            val builder=AlertDialog.Builder(context)
            builder.setTitle("Select Image")
            builder.setMessage("Choose your option?")
            builder.setPositiveButton("Gallery") { dialog, which ->
                dialog.dismiss()
                val intent=Intent(Intent.ACTION_PICK)
                intent.type="image/*"

                startActivityForResult(intent,REQUEST_IMAGE_GALLERY)
            }

            builder.setNegativeButton("Camera") { dialog, which ->
                dialog.dismiss()

               Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult( Intent(MediaStore.ACTION_IMAGE_CAPTURE),REQUEST_IMAGE_CAMERA);

            }
            val dialog:AlertDialog=builder.create()
            dialog.show()
        }
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_GALLERY && resultCode == Activity.RESULT_OK && data!=null) {
            imageBitmap=data.data as Bitmap

            disppic.setImageURI(data.data)

    }
        if (requestCode == REQUEST_IMAGE_CAMERA && resultCode == Activity.RESULT_OK && data!=null) {
            imageBitmap=data.extras?.get("data") as Bitmap

            disppic.setImageBitmap(imageBitmap)

        }
        else {
            Toast.makeText(context,"Something went wrong",Toast.LENGTH_SHORT).show()
        }
}


    private  fun showPhoneNumberExistAlert(isNotValid:Boolean) {
        val PhoneNumber = view?.findViewById(R.id.editTextPhone4) as EditText
        PhoneNumber.error=if(isNotValid) "Phone Number should contain 10 digitand start with 0" else null
    }

    private fun ShowNameExistAlert(isNotValid: Boolean){
        val FullName = view?.findViewById(R.id.editTextTextPersonName) as EditText
        FullName.error=if(isNotValid)"You should Insert a name" else null
    }
    private fun showTextMinimalAlert(isNotValid: Boolean){
        val password = view?.findViewById(R.id.editTextTextPassword2) as EditText
       password.error=if(isNotValid)"password should have more than 8 characters" else null

    }
    private fun showPasswordConfirmAlert(isNotValid: Boolean){
        val Confirmpassword = view?.findViewById(R.id.editTextTextPassword3) as EditText
        Confirmpassword.error=if(isNotValid)"Password does not match" else null
    }



    private fun addaccountStep01(body: MultipartBody.Part, user:MultipartBody.Part) {

        CoroutineScope(Dispatchers.IO).launch {
            val response = RetrofitService.endpoint.addPDV(body,user)
            withContext(Dispatchers.Main) {
                // binding.progressBar.visibility= View.INVISIBLE
                if (response.isSuccessful) {
                    findNavController().navigate(R.id.action_registerStepp012_to_registerStep022)
                    Toast.makeText(requireActivity(), "sucess", Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(requireActivity(), "une erreur", Toast.LENGTH_SHORT).show()
                }

            }



        }
    }

}
