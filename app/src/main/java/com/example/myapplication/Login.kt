package com.example.myapplication


import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.myapplication.R


class Login : AppCompatActivity() {
    private lateinit var navController:NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val navHostFragment=supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController=navHostFragment.navController
        /*
        val buttonSignin=findViewById<Button>(R.id.button4)

        buttonSignin.setOnClickListener{

            val mFragment = registerStepp01()
            val fragment : Fragment? = null
            supportFragmentManager.findFragmentByTag(registerStepp01::class.java.simpleName)
            if (fragment !is Fragment){

                supportFragmentManager.beginTransaction()
                    .replace(R.id.container,mFragment,mFragment::class.java.simpleName)
                    .commit()


            }




        }*/


    }
}