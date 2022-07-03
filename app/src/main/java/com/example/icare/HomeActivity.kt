package com.example.icare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.icare.fragments.DoctorFragment
import com.example.icare.fragments.HomeFragment
import com.example.icare.fragments.InformationFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {

    private lateinit var btnlogout : Button
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportActionBar?.hide()

        btnlogout = findViewById(R.id.logout1)

//  Auth
        auth = FirebaseAuth.getInstance()

//  Click Logout
        btnlogout.setOnClickListener {
            auth.signOut()
            Intent(this, LoginActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
                Toast.makeText(this, "Logout Berhasil!", Toast.LENGTH_SHORT).show()
            }
        }
//  Fragment
        val homeFragment = HomeFragment()
        val informationFragment = InformationFragment()
        val doctorFragment = DoctorFragment()

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        makeCurrentFragment(homeFragment)
        bottomNavigationView.setOnItemReselectedListener {
            when(it.itemId) {
                R.id.page_1 -> makeCurrentFragment(homeFragment)
                R.id.page_2 -> makeCurrentFragment(informationFragment)
                R.id.page_3 -> makeCurrentFragment(doctorFragment)
            }
            true
        }

        }
    private fun makeCurrentFragment(fragment: Fragment) {
        if (fragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fl_wrapper, fragment)
            transaction.commit()
        }
    }
}