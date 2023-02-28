package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class CarsAdapter(val context: Context, var content: List<Voiture>):RecyclerView.Adapter<CarsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentITem = content[position]
        holder.modele.text = currentITem.modele
        Glide.with(context).load(url+content[position].picture).into(holder.picture)
        holder.marque.text = currentITem.marque
        holder.tarif.text = currentITem.tarif.toString()
        holder.itemView.setOnClickListener { view ->
            var content = bundleOf("car" to content[position])
            findNavController(view).navigate(R.id.action_homeFragment_to_carFragment,content)
        }

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var modele : TextView = view.findViewById(R.id.modele)
        var marque: TextView = view.findViewById(R.id.marqueV)
        var tarif : TextView= view.findViewById(R.id.TarifKmHeure)
        var picture: ImageView=view.findViewById(R.id.imageVoiture)
    }

    override fun getItemCount(): Int {
        return content.size
    }

}