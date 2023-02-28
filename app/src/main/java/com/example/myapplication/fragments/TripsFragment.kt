package com.example.myapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.myapplication.AppDataBase
import com.example.myapplication.DataBase.Trajet
import com.example.myapplication.RecyclerViewTrajetAdapter
import com.example.myapplication.databinding.FragmentTripsBinding

class TripsFragment : Fragment() {
    lateinit var binding: FragmentTripsBinding

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
        binding = FragmentTripsBinding.inflate(inflater, container, false)
        val view = binding.root
        val instance =
            Room.databaseBuilder(requireActivity(), AppDataBase::class.java, "fichierTP5")
                .allowMainThreadQueries().build()

        binding.RecyclerViewTrajet.layoutManager = LinearLayoutManager(requireActivity())
        binding.RecyclerViewTrajet.adapter = RecyclerViewTrajetAdapter(requireActivity(), instance.getTrajetDao().getTrajets())
        return view
    }

}