package com.example.split_the_bill

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val billTotal = findViewById<EditText>(R.id.bill_total)
        val numberOfPeople = findViewById<EditText>(R.id.number_of_people)
        val divideButton = findViewById<Button>(R.id.divideButton)
        val resultText = findViewById<TextView>(R.id.resultText)

        divideButton.setOnClickListener {
            val bill = billTotal.text.toString().toDoubleOrNull()
            val people = numberOfPeople.text.toString().toIntOrNull()

            if (bill == null || people == null) {
                Toast.makeText(this, "Please enter valid numbers", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (people == 0) {
                Toast.makeText(this, "Cannot divide by zero!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val result = bill / people
            resultText.text = "Amount per person: $%.2f".format(result)
        }
    }
}
