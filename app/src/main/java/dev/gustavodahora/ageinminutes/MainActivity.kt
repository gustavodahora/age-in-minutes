package dev.gustavodahora.ageinminutes

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import dev.gustavodahora.ageinminutes.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.selectDate.setOnClickListener {
            clickDatePicker()
        }
    }

    private fun clickDatePicker() {
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this, {
                _, selectedYear, selectedMonth, selectedDayOfMonth ->
            Toast.makeText(this, "This chosen year is $selectedYear, the month is $selectedMonth" +
                    " and the day is $selectedDayOfMonth"
                , Toast.LENGTH_LONG).show()

            val selectedDate = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"

            binding.tvSelectedDate.text = selectedDate

            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

            val theDate = sdf.parse(selectedDate) ?: sdf.parse("00/00/0000")
            val currentDate = sdf.parse(sdf.format(System.currentTimeMillis())) ?: sdf.parse("00/00/0000")

            val selectedDateInMinutes = theDate.time / 60000
            val currentDateToMinutes = currentDate.time / 60000

            val calculateDate =  currentDateToMinutes - selectedDateInMinutes
            binding.ageInMinutesValue.text = calculateDate.toString()

        }, year, month, day)

        dpd.datePicker.maxDate = Date().time - 864000000
        dpd.show()
    }
}