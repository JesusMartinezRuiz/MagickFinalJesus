package com.example.magickfinaljesus

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.magickfinaljesus.databinding.RowCartaBinding
import java.util.*

class AdaptadorCartas(val elementos: List<Cartas>, val con: UserMain,var colors:List<Boolean>) :
    RecyclerView.Adapter<AdaptadorCartas.ViewHolder>(), Filterable {


    var elementosFiltrados = elementos
    var allSelected=true
    val color = listOf("blanco", "negro", "azul", "rojo","verde")



    class ViewHolder(val bind:RowCartaBinding)
        : RecyclerView.ViewHolder(bind.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            RowCartaBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val elem = elementosFiltrados[position]

        with(holder.bind){
            nombreRowCarta.text = elem.nombre
            Glide.with(con).load(elem.img).into(ivRowCarta)
            precioRowCarta.text = elem.precio.toString()




//            rowPlaneta.setOnClickListener {
//                con.planetaSeleccionado= elem
//                con.navController.navigate(R.id.FirstFragment)
//            }

        }
    }

    override fun getItemCount(): Int {
        return elementosFiltrados.size
    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val texto = p0.toString()
                if (texto.isEmpty()) {
                    elementosFiltrados = elementos
                } else {
                    val elemetosFiltrados2 = mutableListOf<Cartas>()
                    val textoMinuscula = texto.lowercase(Locale.ROOT)
                    for (e in elementos) {
                        val nombreMinuscula = e.nombre?.lowercase(Locale.ROOT)
                        if(nombreMinuscula!!.contains(textoMinuscula)){
                            elemetosFiltrados2.add(e)
                        }
                    }
                    elementosFiltrados = elemetosFiltrados2
                }



                if (!allSelected){
                    elementosFiltrados = elementosFiltrados.filter{
                        val index = color.indexOf(it.color)
                        colors[index]
                    }
                }else{
                    elementosFiltrados=elementos
                }







                val filterResults = FilterResults()
                filterResults.values = elementosFiltrados

                return filterResults
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                elementosFiltrados = p1?.values as MutableList<Cartas>
                notifyDataSetChanged()
            }
        }
    }
}