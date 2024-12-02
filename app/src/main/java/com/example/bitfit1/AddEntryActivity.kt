package com.example.bitfit1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddEntryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_entry)

        val db = (application as WaterApplication).db

        findViewById<Button>(R.id.btnSave).setOnClickListener {
            val date = findViewById<EditText>(R.id.etDate).text.toString()
            val quantityString = findViewById<EditText>(R.id.etQuantity).text.toString()
            val notes = findViewById<EditText>(R.id.etNotes).text.toString()

            if (date.isEmpty() || quantityString.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val quantity = quantityString.toDoubleOrNull()
            if (quantity == null) {
                Toast.makeText(this, "Invalid quantity", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            lifecycleScope.launch(Dispatchers.IO) {
                val currentDate = date.ifEmpty {
                    SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date())
                }


                db.waterEntryDao().insertEntry(
                    WaterEntryEntity(
                        date = currentDate,
                        quantity = quantity,
                        notes = notes
                    )
                )

                val intent = Intent(this@AddEntryActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}