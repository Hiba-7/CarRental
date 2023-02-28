package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings.Global.putInt
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.edit
import androidx.navigation.fragment.findNavController
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class LoginFragment2 : Fragment() {

    @SuppressLint("CheckResult")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,

        ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_login2, container, false)
        val buttonRegister=view.findViewById(R.id.button4) as Button
        val password = view.findViewById(R.id.editTextTextPassword) as EditText
        val PhoneNumber = view.findViewById(R.id.editTextPhone) as EditText
        val buttonSignin=view.findViewById(R.id.button3) as Button

        //Password validation
        val PasswordStream= RxTextView.textChanges(password)
            .skipInitialValue()
            .map { password ->
                password.isEmpty()
            }
        PasswordStream.subscribe{
            showTextMinimalAlert(it)
        }
        //Phone validation
        val PhoneStream= RxTextView.textChanges(PhoneNumber)
            .skipInitialValue()
            .map { PhoneNumber ->
                PhoneNumber.isEmpty()
            }
        PhoneStream.subscribe{
            showPhoneNumberExistAlert(it)
        }
        //button enable / disable
        val invalidFieldStream= Observable.combineLatest(
            PasswordStream,
            PhoneStream,
            {
                    PhoneInvalid:Boolean,PasswordInvalid:Boolean->
                !PasswordInvalid &&  !PhoneInvalid
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

        buttonSignin.setOnClickListener{
            buttonSignin.visibility = View.VISIBLE

            login(PhoneNumber.text.toString(), password.text.toString())
        }
        buttonRegister.setOnClickListener{

            findNavController().navigate(R.id.action_loginFragment22_to_registerStepp012)

        }

        return view
    }

    private fun login(phone_number: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            //val buttonSignin=view?.findViewById(R.id.button3) as Button

            val response =  RetrofitService.endpoint.verifyUser(phone_number,password)
            withContext(Dispatchers.Main) {
                // buttonSignin?.visibility = View.INVISIBLE
                if(response.isSuccessful) {

                    val user = response.body()
                    if(user!=null) {
                        val pref = context?.getSharedPreferences("user", Context.MODE_PRIVATE)
                        pref?.edit {
                            putInt("user_id", user.user_id!!)
                            putBoolean("connected",true)
                        }

                        val intent = Intent(activity, MainActivity::class.java)
                        startActivity(intent)
                        activity?.finish()

                    }
                    else {
                        Toast.makeText(context,"Email ou Mot de passe erronés ",Toast.LENGTH_SHORT).show()
                    }
                }
                else {
                    Toast.makeText(context,"Une erreur s'est produite",Toast.LENGTH_SHORT).show()
                }


            }

        }

    }
    private  fun showPhoneNumberExistAlert(isNotValid:Boolean) {
        val PhoneNumber = view?.findViewById(R.id.editTextPhone) as EditText
        PhoneNumber.error=if(isNotValid) "Phone Number should contain 10 digitand start with 0" else null
    }

    private fun showTextMinimalAlert(isNotValid: Boolean){
        val password = view?.findViewById(R.id.editTextTextPassword) as EditText
        password.error=if(isNotValid)"password should have more than 8 characters" else null

    }
}