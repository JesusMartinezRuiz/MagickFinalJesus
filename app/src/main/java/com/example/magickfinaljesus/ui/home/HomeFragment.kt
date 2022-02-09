package com.example.magickfinaljesus.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.SearchView
import android.widget.Switch
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.magickfinaljesus.AdaptadorCartas
import com.example.magickfinaljesus.Cartas
import com.example.magickfinaljesus.UserMain
import com.example.magickfinaljesus.databinding.FragmentHomeBinding
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.Serializable

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    lateinit var menu: Menu

    val ma by lazy{
        activity as UserMain
    }
    private val binding get() = _binding!!

    lateinit var recycler: RecyclerView
    lateinit var lista:ArrayList<Cartas>
    private lateinit var db_ref: DatabaseReference
    private lateinit var sto_ref: StorageReference


    // This property is only valid between onCreateView and
    // onDestroyView.

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        lateinit var menu: Menu


        db_ref= FirebaseDatabase.getInstance().getReference()
        sto_ref= FirebaseStorage.getInstance().getReference()


       var listaCheck = listOf(
            binding.checkBlancoCartas,binding.checkNegroCartas,binding.checkAzulCartas,binding.checkRojoCartas,binding.checkVerdeCartas
        )


        var blanco=binding.checkBlancoCartas
        var negro=binding.checkNegroCartas
        var azul=binding.checkAzulCartas
        var rojo=binding.checkRojoCartas
        var verde=binding.checkVerdeCartas



        listaCheck.forEach {
            it.isEnabled = false

        }

        binding.swiCartas.setOnCheckedChangeListener { compoundButton, b ->
            listaCheck.forEach {
                it.isEnabled = b
                it.isChecked=false
            }

            ma.adaptadorCarta.allSelected = !b
            refreshFilter()
        }

        binding.checkAzulCartas.setOnClickListener {
                ma.adaptadorCarta.azul=true
            refreshFilter()
        }


//        listaCheck.forEach {
//            it.setOnClickListener {
//
//                refreshFilter()
//            }
//        }



        return root
    }

    override fun onStart() {
        super.onStart()


        binding.rvComprarCartas.apply {
            adapter= ma.adaptadorCarta
            layoutManager=LinearLayoutManager(ma)
            setHasFixedSize(true)
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    fun refreshFilter(){
        ma.adaptadorCarta.filter.filter("")
    }




}