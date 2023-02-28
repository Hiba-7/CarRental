package com.example.myapplication

import android.Manifest
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.FragmentCarBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random



class CarFragment : Fragment() {
    lateinit var binding:FragmentCarBinding
    var longitude: Double? = null
    var latitude: Double? =  null

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCarBinding.inflate(inflater, container, false)
        val view = binding.root
        val editTextDate = view.findViewById(R.id.editTextDate) as EditText
        val editTextTime = view.findViewById(R.id.editTextTime) as EditText
        // read only
        editTextDate.inputType = InputType.TYPE_NULL
        editTextTime.inputType = InputType.TYPE_NULL

        // Date
        editTextDate.setOnClickListener {
            val cal = Calendar.getInstance()
            val picker = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, month)
                cal.set(Calendar.DAY_OF_MONTH, day)
                editTextDate.setText(SimpleDateFormat("dd/MM/yyyy").format(cal.time))
            }

            DatePickerDialog(
                requireActivity(), picker, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(
                    Calendar.DAY_OF_MONTH
                )
            ).show()

        }

        // TIme
        editTextTime.setOnClickListener {
            // Time
            val cal = Calendar.getInstance()
            val picker = TimePickerDialog.OnTimeSetListener { timePicker, hour, time ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, time)
                editTextTime.setText(SimpleDateFormat("HH:mm").format(cal.time))
            }

            TimePickerDialog(
                requireActivity(), picker, cal.get(Calendar.HOUR_OF_DAY), cal.get(
                    Calendar.MINUTE
                ), true
            ).show()

        }
        return view
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val car = arguments?.getSerializable("car") as Voiture
        if (car != null) {
            binding.marque.text = car.marque
            binding.modele.text = car.modele
            binding.moteur.text = car.moteur
            binding.tarif.text = car.tarif.toString()
            binding.capacity.text=car.capacite.toString()+" person"

            Glide.with(requireActivity()).load(url + car.picture).into(binding.imageView)
            binding.availability.text = car.availability

        }
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()

        }
        val location = getLastKnownLocation()

        binding.locationButton.setOnClickListener {
            val uri = "http://maps.google.com/maps?saddr=&daddr=${car.latitude},${car.longtitude}"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity")
            ContextCompat.startActivity(requireActivity(), intent, null)
        }
        binding.disconnect.setOnClickListener{
            var date=binding.editTextDate.text.toString()
            var time=binding.editTextTime.text.toString()
            var iduser = 1
            var idCar = car.id

            val pin = List(4) { Random.nextInt(10) }.joinToString("")
            var reservation = Reservation(null,idCar,iduser,date,time,pin)
            addReservation(reservation)
            val unlockmodel=Unlock(id=reservation.idUserFk,marque=car.marque, modele=car.modele, tarif=car.tarif , picture = car.picture,pin=reservation.pin)
            var content = bundleOf("car" to unlockmodel)

            findNavController().navigate(R.id.action_carFragment_to_unLockCar,content)

        }

    }


    @SuppressLint("MissingPermission")
    fun getLastKnownLocation() {
        fusedLocationClient.lastLocation .addOnSuccessListener { location->
            if (location != null) {
                longitude=location.longitude
                latitude=location.latitude
            }
        }

    }



    /* private fun CarAfterReservation(voiture: Voiture) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = RetrofitService.endpoint.CarAfterReservation(voiture)
            withContext(Dispatchers.Main) {
                // binding.progressBar.visibility= View.INVISIBLE
                if (response.isSuccessful) {

                    Toast.makeText(requireActivity(), "sucess", Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(requireActivity(), "une erreur", Toast.LENGTH_SHORT).show()
                }

            }



        }
    }
*/
    private fun addReservation(reservation: Reservation) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = RetrofitService.endpoint.addReservation(reservation)
            withContext(Dispatchers.Main) {
                // binding.progressBar.visibility= View.INVISIBLE
                if (response.isSuccessful) {

                    Toast.makeText(requireActivity(), "sucess", Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(requireActivity(), "une erreur", Toast.LENGTH_SHORT).show()
                }

            }



        }
    }
}