package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.myapplication.DataBase.Trajet
import com.example.myapplication.databinding.FragmentCarBinding
import com.example.myapplication.databinding.FragmentEndTripBinding
import com.example.myapplication.databinding.FragmentHomeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import java.text.SimpleDateFormat
import java.util.*

class EndTrip : Fragment() {
    lateinit var binding: FragmentEndTripBinding
    var id_car: Int = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEndTripBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val car = arguments?.getSerializable("car") as Unlock
        binding.modele.text = car.modele
        binding.marque.text = car.marque
        binding.price.text = car.tarif.toString()
        val instance = Room.databaseBuilder(requireActivity(), AppDataBase::class.java,"fichierTP5").allowMainThreadQueries().build()
        binding.pin.setOnClickListener{
            Toast.makeText(requireActivity(), "hello", Toast.LENGTH_SHORT).show()
            val trajet = Trajet(
                dateJ = getCurrentDate(),
                heureD = "10:10 am",
                heureF = getCurrentTime(),
                TarifT = car.tarif?: 0 ,
                CarMarque = car.marque?:"Marque",
                CarModele = car.modele?:"Modele",

            )
            instance.getTrajetDao().addTrajet(trajet)
        }
    }




        fun getCurrentDate(): String {
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val currentDate = Date()
            return dateFormat.format(currentDate)
        }
    private fun getCurrentTime(): String {
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        val currentTime = Date()
        return sdf.format(currentTime)
    }
}


