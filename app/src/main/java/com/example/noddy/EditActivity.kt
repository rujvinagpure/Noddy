package com.example.noddy

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.DatePicker
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.fragment.app.DialogFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.DateFormat
import java.util.*

class EditActivity : AppCompatActivity() , DatePickerDialog.OnDateSetListener {

    lateinit var datepicker1: TextView
    lateinit var redfab:FloatingActionButton
    lateinit var bluefab:FloatingActionButton
    lateinit var greenfab:FloatingActionButton
    lateinit var pinkfab:FloatingActionButton
    lateinit var yellowfab:FloatingActionButton
    lateinit var cardview:CardView
    lateinit var phoneId:String
    lateinit var title:TextView
    lateinit var descr:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        redfab = findViewById<FloatingActionButton>(R.id.redfab)
        bluefab = findViewById<FloatingActionButton>(R.id.bluefab)
        yellowfab = findViewById<FloatingActionButton>(R.id.yellowfab)
        greenfab = findViewById<FloatingActionButton>(R.id.greenfab)
        pinkfab = findViewById<FloatingActionButton>(R.id.pinkfab)
        datepicker1 = findViewById<TextView>(R.id.datepicker)
        title = findViewById<TextView>(R.id.title)
        descr = findViewById<TextView>(R.id.description)
        phoneId = Settings.Secure.getString(this.contentResolver,Settings.Secure.ANDROID_ID)
        Log.d("manik",phoneId)

        cardview=findViewById<CardView>(R.id.cardview)

        colorChoose()
        datepicker1.setOnClickListener {

            val dialogfragment:DialogFragment = Datepicker()
            dialogfragment.show(supportFragmentManager,"date picker")

        }
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        val c:Calendar = Calendar.getInstance()
        c.set(Calendar.YEAR,p1)
        c.set(Calendar.MONTH,p2)
        c.set(Calendar.DAY_OF_MONTH,p3)
        val currentDate:String = DateFormat.getDateInstance(DateFormat.FULL).format(c.time)
        datepicker1.setText(currentDate)
    }


    private fun colorChoose(){
        redfab.setOnClickListener {
           cardview.setCardBackgroundColor(Color.parseColor("#ff8a80"))
        }

        bluefab.setOnClickListener {
           cardview.setCardBackgroundColor(Color.parseColor("#80d8ff"))
        }

        yellowfab.setOnClickListener {
           cardview.setCardBackgroundColor(Color.parseColor("#ffa000"))
        }

        pinkfab.setOnClickListener {
           cardview.setCardBackgroundColor(Color.parseColor("#b388ff"))
        }

        greenfab.setOnClickListener {
           cardview.setCardBackgroundColor(Color.parseColor("#98ff98"))
        }

    }
}

