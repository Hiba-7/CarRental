package com.example.myapplication

data class credit_card(
    var Credit_Card_ID : Int?,
    var cvc :String,
    var card_number:String,
    var full_name: String,
	var expiry_date : String,
	var idUserFk : Int?
)
