package com.example.myapplication

data class Reservation(
    var idReservation:Int?,
    var idCarFk : Int,
    var idUserFk:Int,
    var date:String,
    var time:String,
    var pin : String
)
