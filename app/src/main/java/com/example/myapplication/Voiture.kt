package com.example.myapplication

import java.io.Serializable
data class Voiture( var id:Int,
                    var marque:String,
                    var modele : String,
                   var moteur:String,
                   var tarif:Int,
                   var availability:String,
                   var picture:String,
                   var longtitude:Double?,
                   var latitude:Double?,
                   var capacite:Int?,
                    var pin:String?
): Serializable {

}