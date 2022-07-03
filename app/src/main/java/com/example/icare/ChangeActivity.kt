package com.example.icare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ChangeActivity : AppCompatActivity() {

    private lateinit var ecDate : EditText
    private lateinit var ecPoli : EditText
    private lateinit var ecDoctor : EditText
    private lateinit var ecTime : EditText
    private lateinit var btnSave : Button
    private lateinit var btnApp : Button

    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change)

        ecDate = findViewById(R.id.erDate)
        ecPoli = findViewById(R.id.erPoliklinik)
        ecDoctor = findViewById(R.id.erDoctor)
        ecTime = findViewById(R.id.erTime)
        btnSave = findViewById(R.id.btnchange)
        btnApp = findViewById(R.id.backupd)

        setData()

        btnSave.setOnClickListener {
            val sDate = ecDate.text.toString()
            val sPoli = ecPoli.text.toString()
            val sDoctor = ecDoctor.text.toString()
            val sTime = ecTime.text.toString()

            val updateMap = mapOf(
                "date" to sDate,
                "poli" to sPoli,
                "doctor" to sDoctor,
                "time" to sTime
            )

            val userId = FirebaseAuth.getInstance().currentUser!!.uid
            db.collection("appointment").document(userId).update(updateMap)

            Toast.makeText(this, "Appointment Updated!", Toast.LENGTH_SHORT).show()
        }

        btnApp.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }

    }

    private fun setData() {
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        val ref = db.collection("appointment").document(userId)
        ref.get().addOnSuccessListener {
            if (it != null) {
                val date = it.data?.get("date")?.toString()
                val poli = it.data?.get("poli")?.toString()
                val doctor = it.data?.get("doctor")?.toString()
                val time = it.data?.get("time")?.toString()

                ecDate.setText(date)
                ecPoli.setText(poli)
                ecDoctor.setText(doctor)
                ecTime.setText(time)
            }
        }
            .addOnFailureListener{
                Toast.makeText(this, "Appointment Failed Updated!", Toast.LENGTH_SHORT).show()
            }
    }
}