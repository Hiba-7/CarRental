package com.example.myapplication

import java.io.Serializable
data class Unlock( var id:Int?,
                    var marque:String?,
                    var modele : String?,
                    var tarif:Int?,
                    var picture:String?,
                    var pin:String?,
): Serializable {

}