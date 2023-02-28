package com.example.myapplication.DataBase
import androidx.room.*
import com.example.myapplication.DataBase.Trajet


@Dao
interface TrajetDao {
    @Insert
    fun addTrajet(trajet: Trajet)

    @Delete
    fun deleteTrajet(trajet: Trajet)

    @Update
    fun updateTrajet(trajet: Trajet)

    @Query("SELECT * FROM trajets ")
    fun getTrajets():List<Trajet>

}