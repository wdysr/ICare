package com.example.icare

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DeleteActivity : AppCompatActivity() {

    private lateinit var edDate : TextView
    private lateinit var edPoli : TextView
    private lateinit var edDoctor : TextView
    private lateinit var edTime : TextView
    private lateinit var deleteclick : Button

    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete)

        edDate = findViewById(R.id.erDate)
        edPoli = findViewById(R.id.erPoliklinik)
        edDoctor = findViewById(R.id.erDoctor)
        edTime = findViewById(R.id.erTime)
        deleteclick = findViewById(R.id.btndelete)

        setData()

        deleteclick.setOnClickListener {
            val mapDelete = mapOf(
                "date" to FieldValue.delete(),
                "poli" to FieldValue.delete(),
                "doctor" to FieldValue.delete(),
                "time" to FieldValue.delete()
            )

            val userId = FirebaseAuth.getInstance().currentUser!!.uid
            db.collection("appointment").document(userId).update(mapDelete)
                .addOnSuccessListener {
                    Toast.makeText(this, "Appointment Completed!", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener{
                    Toast.makeText(this, "Appointment not Completed!", Toast.LENGTH_SHORT).show()
                }
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

                edDate.text = date
                edPoli.text = poli
                edDoctor.text = doctor
                edTime.text = time
            }
        }
    }
}