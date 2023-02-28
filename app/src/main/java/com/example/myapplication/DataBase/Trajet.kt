    package com.example.myapplication.DataBase

    import java.sql.Time
    import java.util.Date
    import androidx.room.Entity
    import androidx.room.PrimaryKey
    import java.io.Serializable

    @Entity( tableName = "trajets")
    data class Trajet(
        @PrimaryKey(autoGenerate = true) val id: Int = 0,
        var dateJ: String,
        var heureD:String,
        var heureF:String,
        var TarifT: Int ,
        var CarModele : String ,
        var CarMarque : String ): Serializable
