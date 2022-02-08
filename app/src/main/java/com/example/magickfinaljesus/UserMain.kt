package com.example.magickfinaljesus

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.magickfinaljesus.databinding.ActivityUserMainBinding
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class UserMain : AppCompatActivity() {

    private lateinit var binding: ActivityUserMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var navController: NavController
    lateinit var lista:ArrayList<Cartas>
    private lateinit var db_ref: DatabaseReference
    private lateinit var sto_ref: StorageReference
    val adaptador by lazy{
        AdaptadorCartas(lista, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db_ref= FirebaseDatabase.getInstance().getReference()
        sto_ref= FirebaseStorage.getInstance().getReference()
        lista=ArrayList<Cartas>()

        binding = ActivityUserMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_user_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_cartas, R.id.navigation_eventos, R.id.navigation_cesta
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        db_ref.child("tienda")
            .child("cartas")
            .addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    lista.clear()
                    snapshot.children.forEach { hijo->
                        val pojo_usuario=hijo?.getValue(Cartas::class.java)
                        lista.add(pojo_usuario!!)
                    }

                }
                override fun onCancelled(error: DatabaseError) {

                }
            })







    }

}