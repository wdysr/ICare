package com.example.icare

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

class AppointmentActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var backbtn : Button
    private lateinit var btnappoint : Button
    private lateinit var etDate : EditText
    private lateinit var etPoli : EditText
    private lateinit var etDoctor : EditText
    private lateinit var etTime : EditText

    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointment)
        supportActionBar?.hide()

        backbtn = findViewById(R.id.backapp)
        btnappoint = findViewById(R.id.btncreate)
        etDate = findViewById(R.id.erDate)
        etPoli = findViewById(R.id.erPoliklinik)
        etDoctor = findViewById(R.id.erDoctor)
        etTime = findViewById(R.id.erTime)

        val myCalendar = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener {view, year, month, dayofmonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayofmonth)
            updateLabel(myCalendar)
        }

        etDate.setOnClickListener{
            DatePickerDialog(this, datePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }
        backbtn.setOnClickListener(this)
        btnappoint.setOnClickListener {
            val aDate = etDate.text.toString().trim()
            val aPoli = etPoli.text.toString().trim()
            val aDoctor = etDoctor.text.toString().trim()
            val aTime = etTime.text.toString().trim()

            val userAppointment = hashMapOf(
                "date" to aDate,
                "poli" to aPoli,
                "doctor" to aDoctor,
                "time" to aTime
            )

            val userId = FirebaseAuth.getInstance().currentUser!!.uid

            db.collection("appointment").document(userId).set(userAppointment)
                .addOnSuccessListener {
                    Toast.makeText(this, "Appointment Success!", Toast.LENGTH_SHORT).show()
                    etDate.text.clear()
                    etPoli.text.clear()
                    etDoctor.text.clear()
                    etTime.text.clear()
                }
                .addOnFailureListener{
                    Toast.makeText(this, "Appointment Failed", Toast.LENGTH_SHORT).show()
                }
        }

    }
    private fun updateLabel (myCalendar: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        etDate.setText(sdf.format(myCalendar.time))
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.backapp -> {
                    val tombolkembali = Intent(this, HomeActivity::class.java)
                    startActivity(tombolkembali)
                }
            }
        }
    }
}