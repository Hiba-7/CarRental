package com.example.myapplication

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.myapplication.BluetoothList.Companion.UUID_STRING
import com.example.myapplication.databinding.FragmentUnLockCarBinding
import java.io.IOException
import java.io.OutputStream
import java.util.*


class BluetoothList : Fragment() {

    private var m_bluetoothAdapter: BluetoothAdapter? = null
    private lateinit var m_pairedDevices: Set<BluetoothDevice>
    private val REQUEST_ENABLE_BLUETOOTH = 1

    companion object {
        val EXTRA_ADDRESS: String = "Device_address"

        val UUID_STRING: String = "00001101-0000-1000-8000-00805F9B34FB" // UUID for serial port profile (SPP)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_bluetooth_list, container, false)
        val reservation = arguments?.getSerializable("car") as Unlock

        if (reservation != null) {
            var PIN_CODE = reservation.pin.toString()
        }
        m_bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if(m_bluetoothAdapter == null) {
            Toast.makeText(requireActivity(), "This device doesn't support bluetooth", Toast.LENGTH_SHORT).show()
            // return
        }
        if(!m_bluetoothAdapter!!.isEnabled) {
            val enableBluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBluetoothIntent, REQUEST_ENABLE_BLUETOOTH)
        }

// Find the button in the inflated layout file using the view object
        val select_device_refresh2 = view?.findViewById<Button>(R.id.select_device_refresh)

// Set the click listener on the button
        select_device_refresh2?.setOnClickListener {
            pairedDeviceList()
        }
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       val reservation = arguments?.getSerializable("car") as Unlock
     //   var content = bundleOf("car" to reservation)
      //  findNavController().navigate(R.id.action_bluetoothList_to_endTrip,content)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_ENABLE_BLUETOOTH) {
            if (resultCode == AppCompatActivity.RESULT_OK) {
                if (m_bluetoothAdapter!!.isEnabled) {
                    // toast("Bluetooth has been enabled")
                } else {
                    //toast("Bluetooth has been disabled")
                }
            } else if (resultCode == AppCompatActivity.RESULT_CANCELED) {
                //toast("Bluetooth enabling has been canceled")
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun connectToDevice(device: BluetoothDevice) {
        val uuid = UUID.fromString(BluetoothList.UUID_STRING)
        val socket: BluetoothSocket = device.createRfcommSocketToServiceRecord(uuid)
        val reservation = arguments?.getSerializable("car") as Unlock

            var PIN_CODE = reservation.pin.toString()

        try {
            socket.connect()
            Log.i("Bluetooth", "Connected to device ${device.name}")
            val outputStream: OutputStream = socket.outputStream
            outputStream.write(PIN_CODE.toByteArray())

            Log.i("Bluetooth", "Sent PIN code ${PIN_CODE} to device ${device.name}")
            Toast.makeText(requireActivity(), "Sent PIN code ${PIN_CODE}  to device ${device.name}", Toast.LENGTH_SHORT).show()

        } catch (e: IOException) {
            Log.e("Bluetooth", "Error connecting to device ${device.name}: ${e.message}")
        }
    }

    @SuppressLint("MissingPermission")
    private fun pairedDeviceList() {
        m_pairedDevices = m_bluetoothAdapter!!.bondedDevices
        val list : ArrayList<BluetoothDevice> = ArrayList()

        if (!m_pairedDevices.isEmpty()) {
            for (device: BluetoothDevice in m_pairedDevices) {
                list.add(device)
                Log.i("device", ""+device.name)
            }
        } else {
            Toast.makeText(requireActivity(), "no paired bluetooth devices found", Toast.LENGTH_SHORT).show()
        }

        val adapter = ArrayAdapter(requireActivity(), android.R.layout.simple_list_item_1, list)
        val select_device_list: ListView = view?.findViewById(R.id.simpleListView)!!

        select_device_list.adapter = adapter
        select_device_list.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val device: BluetoothDevice = list[position]
            val address: String = device.address
            val name: String = device.name
            Toast.makeText(requireActivity(), "Connecting to $name", Toast.LENGTH_SHORT).show()
            connectToDevice(device)
        }
    }

}