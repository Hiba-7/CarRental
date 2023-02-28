package com.example.myapplication.DataBase
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.example.myapplication.AppDataBase
import java.util.*


class TrajetModel( private val  context: Context): ViewModel() {

    val trajets = loadData(context)

    private fun loadData(context: Context): List<Trajet> {
        val instance = Room.databaseBuilder(context, AppDataBase::class.java,"TrajetDatabase").allowMainThreadQueries().build()
        return instance.getTrajetDao().getTrajets()
    }

}