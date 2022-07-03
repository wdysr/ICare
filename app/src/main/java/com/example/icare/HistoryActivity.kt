package com.example.icare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HistoryActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var updateclick : Button
    private lateinit var deleteclick : Button
    private lateinit var backclick : Button
    private lateinit var etDate : TextView
    private lateinit var etPoli : TextView
    private lateinit var etDoctor : TextView
    private lateinit var etTime : TextView
    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        updateclick = findViewById(R.id.update1)
        deleteclick = findViewById(R.id.deletebtn)
        backclick = findViewById(R.id.backapk)
        etDate = findViewById(R.id.erDate)
        etPoli = findViewById(R.id.erPoliklinik)
        etDoctor = findViewById(R.id.erDoctor)
        etTime = findViewById(R.id.erTime)

        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        val ref = db.collection("appointment").document(userId)
        ref.get().addOnSuccessListener {
            if (it != null) {
                val date = it.data?.get("date")?.toString()
                val poli = it.data?.get("poli")?.toString()
                val doctor = it.data?.get("doctor")?.toString()
                val time = it.data?.get("time")?.toString()

                etDate.text = date
                etPoli.text = poli
                etDoctor.text = doctor
                etTime.text = time
            }
        }
            .addOnFailureListener{
                Toast.makeText(this, "Tidak ada Appointment", Toast.LENGTH_SHORT).show()
            }
        updateclick.setOnClickListener {
            val intent = Intent(this, ChangeActivity::class.java)
            startActivity(intent)
        }
        deleteclick.setOnClickListener {
            val intent = Intent(this, DeleteActivity::class.java)
            startActivity(intent)
        }
        backclick.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onClick(v: View?) {
    }
}