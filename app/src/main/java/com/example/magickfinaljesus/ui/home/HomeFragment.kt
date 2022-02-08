package com.example.magickfinaljesus.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
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

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    val ma by lazy{
        activity as UserMain
    }

    lateinit var recycler: RecyclerView
    lateinit var lista:ArrayList<Cartas>
    private lateinit var db_ref: DatabaseReference
    private lateinit var sto_ref: StorageReference


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

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
        val ma by lazy{
            activity as UserMain
        }

        db_ref= FirebaseDatabase.getInstance().getReference()
        sto_ref= FirebaseStorage.getInstance().getReference()
        lista=ArrayList<Cartas>()


        val activarFiltro: Switch = binding.swiCartas
        val blanco:CheckBox=binding.checkBlancoCartas
        val negro:CheckBox=binding.checkNegroCartas
        val azul:CheckBox=binding.checkAzulCartas
        val rojo:CheckBox=binding.checkRojoCartas
        val verde:CheckBox=binding.checkVerdeCartas

        var listaCheck = listOf(
            blanco,negro,azul,rojo,verde
        )

        listaCheck.forEach {
            it.isEnabled = false
        }

        activarFiltro.setOnCheckedChangeListener { compoundButton, b ->
            listaCheck.forEach {
                it.isEnabled = b
                it.isChecked = b
            }

        }


        homeViewModel.text.observe(viewLifecycleOwner, Observer {

        })
        return root
    }

    override fun onStart() {
        super.onStart()


        binding.rvComprarCartas.apply {
            adapter= ma.adaptador
            layoutManager=LinearLayoutManager(ma)
            setHasFixedSize(true)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}