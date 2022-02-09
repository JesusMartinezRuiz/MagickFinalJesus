package com.example.magickfinaljesus

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.magickfinaljesus.databinding.RowEventosBinding


class AdaptadorEventos(val elementos: List<Eventos>, val con: UserMain) :
    RecyclerView.Adapter<AdaptadorEventos.ViewHolder>(), Filterable {


    var elementosFiltrados = elementos


    class ViewHolder(val bind: RowEventosBinding)
        : RecyclerView.ViewHolder(bind.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            RowEventosBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val elem = elementosFiltrados[position]

        with(holder.bind){
            rowNombreEvento.text=elem.nombre
            rowAforoMaxEvento.text= elem.aforo_max.toString()
            rowAforoOcupadoEvento.text= elem.aforo_ocupado.toString()
            rowFechaEvento.text= elem.fecha

            Glide.with(con).load(elem.img).into(rowIvEvento)


        }



    }

    override fun getItemCount(): Int {
        return elementosFiltrados.size
    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(p0: CharSequence?): FilterResults {
//                val texto = p0.toString()
//                if (texto.isEmpty()) {
//                    elementosFiltrados = elementos
//                } else {
//                    val elemetosFiltrados2 = mutableListOf<Cartas>()
//                    val textoMinuscula = texto.lowercase(Locale.ROOT)
//                    for (e in elementos) {
//                        val nombreMinuscula = e.nombre?.lowercase(Locale.ROOT)
//                        if(nombreMinuscula!!.contains(textoMinuscula)){
//                            elemetosFiltrados2.add(e)
//                        }
//                    }
//                    elementosFiltrados = elemetosFiltrados2
//                }




                val filterResults = FilterResults()
                filterResults.values = elementosFiltrados

                return filterResults
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                elementosFiltrados = p1?.values as MutableList<Eventos>
                notifyDataSetChanged()
            }
        }
    }
}