package com.example.myapplication.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.*
import com.example.myapplication.databinding.FragmentReservationsBinding
import com.example.myapplication.databinding.FragmentTripsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ReservationsFragment : Fragment() {

    lateinit var binding: FragmentReservationsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentReservationsBinding.inflate(inflater, container, false)
        val view = binding.root
        val pref = context?.getSharedPreferences("user", Context.MODE_PRIVATE)
        val id = pref?.getInt("user_id",0)
        getreservations(id)

        return view
    }

    private fun getreservations(id : Int?) {
        CoroutineScope(Dispatchers.IO).launch {

            val response = RetrofitService.endpoint.getreservations(1)
            withContext(Dispatchers.Main) {
                // binding.progressBar.visibility= View.INVISIBLE
                if (response.isSuccessful) {
                    val reservations = response.body()
                    if (reservations != null) {
                        binding.recylcerView.layoutManager = LinearLayoutManager(requireActivity())
                        binding.recylcerView.adapter = ReservationAdapter(requireActivity(), reservations)
                    }
                }
                else {
                    Toast.makeText(requireActivity(),"une erreur", Toast.LENGTH_SHORT).show()}
            }


        }
    }




}