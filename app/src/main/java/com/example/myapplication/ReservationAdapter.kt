package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class ReservationAdapter (val context: Context, var content: List<ReservationCars>):RecyclerView.Adapter<ReservationAdapter.ReservationViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view_reservation, parent, false)

        return ReservationViewHolder(view)
    }
    override fun onBindViewHolder(holder: ReservationViewHolder, position: Int) {

        val currentITem = content[position]
        holder.date.text=currentITem.date
        holder.time.text=currentITem.time
        holder.moteur.text=currentITem.moteur
        holder.marque.text=currentITem.marque
        holder.tarif.text=currentITem.tarif.toString()
        Glide.with(context).load(url+content[position].picture).into(holder.picture)
        holder.pin.text="pin code : "+ currentITem.pin
    }
    override fun getItemCount(): Int {
        return content.size
    }
    class ReservationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var marque : TextView = view.findViewById(R.id.modele)
        var moteur: TextView = view.findViewById(R.id.marqueV)
        var tarif : TextView = view.findViewById(R.id.TarifKmHeure)
        var date : TextView = view.findViewById(R.id.date)
        var time : TextView = view.findViewById(R.id.time)
        var picture: ImageView =view.findViewById(R.id.imageVoiture)
        var pin : Button = view.findViewById(R.id.pin)
    }
}