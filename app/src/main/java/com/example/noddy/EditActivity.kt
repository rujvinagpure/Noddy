package com.example.noddy

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.core.view.get
import androidx.fragment.app.DialogFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.FirebaseDatabase
import java.text.DateFormat
import java.util.*

class EditActivity : AppCompatActivity() , DatePickerDialog.OnDateSetListener {

    lateinit var datepicker1: TextView
    lateinit var redfab:FloatingActionButton
    lateinit var bluefab:FloatingActionButton
    lateinit var greenfab:FloatingActionButton
    lateinit var pinkfab:FloatingActionButton
    lateinit var yellowfab:FloatingActionButton
    lateinit var savebtn:FloatingActionButton
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
        savebtn = findViewById<FloatingActionButton>(R.id.savebtn)
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
        savebtn.setOnClickListener {
            DatebaseUploading()
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
          val c=   redfab.backgroundTintList?.defaultColor
            if (c != null) {
                cardview.setCardBackgroundColor(c)
            }
        }

        bluefab.setOnClickListener {
            val c=   bluefab.backgroundTintList?.defaultColor
            if (c != null) {
                cardview.setCardBackgroundColor(c)
            }
        }

        yellowfab.setOnClickListener {
            val c=   yellowfab.backgroundTintList?.defaultColor
            if (c != null) {
                cardview.setCardBackgroundColor(c)
            }
        }

        pinkfab.setOnClickListener {
            val c=   pinkfab.backgroundTintList?.defaultColor
            if (c != null) {
                cardview.setCardBackgroundColor(c)
            }
        }

        greenfab.setOnClickListener {
            val c=   greenfab.backgroundTintList?.defaultColor
            if (c != null) {
                cardview.setCardBackgroundColor(c)
            }
        }

    }
    private fun DatebaseUploading(){
        Log.d("manik",phoneId)
        val t = title.text.toString()
        val d = descr.text.toString()
        val date = datepicker1.text.toString()
      val c= cardview.cardBackgroundColor.defaultColor
        val ref= FirebaseDatabase.getInstance().getReference(phoneId)

        val UserId = ref.push().key
        val user = User(UserId!!,t,d,date,c)
        ref.child(UserId).setValue(user).addOnCompleteListener {
            Toast.makeText(this,"Succes saved",Toast.LENGTH_LONG).show()

            finish()

        }

    }
}

