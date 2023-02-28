package com.example.myapplication.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.edit
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.myapplication.*
import com.example.myapplication.databinding.FragmentProfileBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.text.SimpleDateFormat
import java.util.*


class ProfileFragment : Fragment()  {

    lateinit var binding: FragmentProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pref = requireContext().getSharedPreferences("user", Context.MODE_PRIVATE)
        var con = pref.getBoolean("connected",false)
        var id = pref.getInt("id",0)

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val user = getuser(id)
                binding.name.text = user.full_name
                binding.phone.text = user.phone_number
                Glide.with(requireActivity()).load(url + user.profile_picture).into(binding.picture)
            } catch (e: HttpException) {
                Toast.makeText(requireActivity(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
        binding.deconnecter.setOnClickListener{
            val pref = context?.getSharedPreferences("user", Context.MODE_PRIVATE)
            pref?.edit {
                putBoolean("connected",false)
            }
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

    }
    suspend fun getuser(id: Int?): user {
        return withContext(Dispatchers.IO) {
            val response = RetrofitService.endpoint.getuser(id)
            (if (response.isSuccessful) {
                val user = response.body()
                user // return the user object
            } else {
                throw HttpException(response)
            })!!
        }
    }

}