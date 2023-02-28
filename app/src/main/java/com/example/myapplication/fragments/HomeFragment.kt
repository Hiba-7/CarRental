package com.example.myapplication.fragments
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.*
import com.example.myapplication.databinding.FragmentHomeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getcars()
    }

    private fun getcars() {

        CoroutineScope(Dispatchers.IO).launch {
            val response = RetrofitService.endpoint.getcars()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val cars = response.body()
                    if (cars != null) {
                        binding.recylcerView.adapter = CarsAdapter(requireActivity(),cars)
                        binding.recylcerView.layoutManager = LinearLayoutManager(requireActivity())
                    }
                }
                else {

                  }
            }


        }
    }


}
    // Referencing and Initializing the button
