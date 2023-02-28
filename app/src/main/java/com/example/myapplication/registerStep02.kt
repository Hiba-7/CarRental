package com.example.myapplication

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class registerStep02 : Fragment() {


    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_register_step02, container, false)
        val buttonSignin=view.findViewById(R.id.pin) as Button
        val CardHolderName=view.findViewById(R.id.editTextTextPersonName2) as EditText
        val cvc = view.findViewById(R.id.editTextNumber2) as EditText
        val cardNumber = view.findViewById(R.id.editTextNumber) as EditText
        val DateCard = view.findViewById(R.id.editTextDate2) as EditText

        DateCard.setOnClickListener{
            val cal = Calendar.getInstance()
            val picker =  DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH,month)
                cal.set(Calendar.DAY_OF_MONTH,day)
                DateCard.setText(SimpleDateFormat("dd/MM/yyyy").format(cal.time))
            }
            DatePickerDialog(
                requireActivity(), picker, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(
                    Calendar.DAY_OF_MONTH
                )
            ).show()
        }



        //Card Holder name validation
        val nameStream= RxTextView.textChanges(CardHolderName)
            .skipInitialValue()
            .map { name ->
                name.isEmpty()
            }
        nameStream.subscribe{
            ShowNameExistAlert(it)
        }
        //CVC validation
        val CVCStream= RxTextView.textChanges(cvc)
            .skipInitialValue()
            .map { cvc ->
                 ( cvc.length != 3)
            }

        CVCStream.subscribe{
            showTextMinimalAlert(it)
        }

        //Card number validation
        val CardNumberstream= RxTextView.textChanges(cardNumber)
            .skipInitialValue()
            .map { cardNumber ->
                ( cardNumber.length != 16)
            }

        CardNumberstream.subscribe{
            CardNumberValidation(it)
        }

        //Button Enable True or False
        val invalidFieldStream= Observable.combineLatest(
            nameStream,
            CVCStream,
            CardNumberstream,

            {
                    nameInvalid:Boolean,CVCInvalid:Boolean, CardNumberInvalid:Boolean ->
                !nameInvalid && !CVCInvalid && !CardNumberInvalid
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
           // var creditCard = arguments?.getSerializable("credit_card") as credit_card

            var CreditCard= credit_card(null,cvc.text.toString(),cardNumber.text.toString(),CardHolderName.text.toString(),DateCard.text.toString(),null)
                addaccountStep02(CreditCard)


        }
        return view
    }

    private fun ShowNameExistAlert(isNotValid: Boolean){
        val CardHolderName=view?.findViewById(R.id.editTextTextPersonName2) as EditText
        CardHolderName.error=if(isNotValid)"You should Insert a name" else null
    }
    private fun CardNumberValidation(isNotValid: Boolean){
        val cardNumber = view?.findViewById(R.id.editTextNumber) as EditText
        cardNumber.error=if(isNotValid)"Incorrect Input" else null
    }
    private fun showTextMinimalAlert(isNotValid: Boolean){
        val cvc = view?.findViewById(R.id.editTextNumber2) as EditText
        cvc.error=if(isNotValid)"Incorrect input" else null
    }



    private fun addaccountStep02(CreditCard:credit_card) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = RetrofitService.endpoint.addaccountStep02(CreditCard)
            withContext(Dispatchers.Main) {
                // binding.progressBar.visibility= View.INVISIBLE
                if (response.isSuccessful) {
                    findNavController().navigate(R.id.action_registerStep022_to_registerStep032)
                    Toast.makeText(requireActivity(), "sucess", Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(requireActivity(), "une erreur", Toast.LENGTH_SHORT).show()
                }

            }



        }
    }
}