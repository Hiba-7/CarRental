package com.example.myapplication
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.myapplication.fragments.ProfileFragment
import com.example.myapplication.fragments.TripsFragment
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.fragments.HomeFragment
import com.example.myapplication.fragments.ReservationsFragment


class MainActivity : AppCompatActivity() {
    private val homeFragment = HomeFragment()
    private val profileFragment = ProfileFragment()
    private val reservationsFragment = TripsFragment()
    private val tripsFragment = ReservationsFragment()
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val pref = getSharedPreferences("user", Context.MODE_PRIVATE)
        var con = pref.getBoolean("connected",false)
        if (!con){
            val intent = Intent(this,Login::class.java)
            this.startActivity(intent)
            finish()
        }
        val navHostFragment=supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        navController=navHostFragment.navController
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId)
            {

                R.id.ic_home -> replaceFragment(homeFragment)
                R.id.ic_profile -> replaceFragment(profileFragment)
                R.id.ic_reservation -> replaceFragment(reservationsFragment)
                R.id.ic_trip -> replaceFragment(tripsFragment)
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        if (fragment != null) {
            if (fragment==homeFragment){
                val intent = Intent(this,MainActivity::class.java)
                this.startActivity(intent)
            }
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment,fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }
}