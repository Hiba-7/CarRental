package com.example.myapplication

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.InputType
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.FragmentCarBinding
import com.example.myapplication.databinding.FragmentUnLockCarBinding
import java.io.IOException
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*
import android.widget.ArrayAdapter;
import androidx.core.os.bundleOf


class UnLockCar : Fragment() {
    lateinit var binding: FragmentUnLockCarBinding

    @SuppressLint("MissingPermission", "MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUnLockCarBinding.inflate(inflater, container, false)
        val view = binding.root
        val car = arguments?.getSerializable("car") as Unlock
        if (car != null) {
            binding.NomVoiture.text = car.marque
            binding.nomVoitureMarqueModLe2.text = car.modele
            binding.TarifKmHeure.text = car.tarif.toString() + " km/heure"
            binding.textView.text=car.pin.toString()

            Glide.with(requireActivity()).load(url + car.picture).into(binding.imageView)

        }



        binding.unlockbutton.setOnClickListener{
            val unlockbluetooth=Unlock(1,"AUDI", "A3", 1345 ,"image",pin=car.pin)
            var content = bundleOf("car" to unlockbluetooth)
            findNavController().navigate(R.id.action_unLockCar_to_bluetoothList,content)
         /*   findNavController().navigate(R.id.action_unLockCar_to_endTrip,content)*/
        }
        return view
    }
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }




}