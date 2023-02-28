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
import com.example.myapplication.DataBase.Trajet
import org.w3c.dom.Text

class RecyclerViewTrajetAdapter(val context: Context,var content:List<Trajet>):RecyclerView.Adapter<RecyclerViewTrajetAdapter.ViewHolder>() {

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(parent.context).inflate(R.layout.itemtraj_view, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentITem=content[position]
        holder.dateJ.text=currentITem.dateJ.toString()
        holder.heureD.text=currentITem.heureD.toString()
        holder.heureF.text=currentITem.heureF.toString()
        holder.tarifButton.text = "prix  ${currentITem.TarifT.toString()}"
        holder.marque.text=currentITem.CarMarque
        holder.modele.text=currentITem.CarModele


    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var dateJ : TextView = view.findViewById(R.id.DateTrajet)
        var heureD: TextView = view.findViewById(R.id.HeureD)
        var heureF : TextView= view.findViewById(R.id.time)
        var tarifButton : Button = view.findViewById(R.id.pin)
        var trajetpicture : ImageView = view.findViewById(R.id.trajetpicture)
        var modele : TextView= view.findViewById(R.id.modele)
        var marque : TextView = view.findViewById(R.id.marqueV)


    }

    override fun getItemCount(): Int {
        return content.size
    }
}