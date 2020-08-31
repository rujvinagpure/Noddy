package com.jerry.noddy

import android.app.AlertDialog
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.WindowManager
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
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
    lateinit var close:FloatingActionButton
    lateinit var cardview:CardView
    lateinit var phoneId:String
    lateinit var title:TextView
    lateinit var descr:TextView
    lateinit var  UserId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_edit)
        redfab = findViewById<FloatingActionButton>(R.id.redfab)
        bluefab = findViewById<FloatingActionButton>(R.id.bluefab)
        yellowfab = findViewById<FloatingActionButton>(R.id.yellowfab)
        greenfab = findViewById<FloatingActionButton>(R.id.greenfab)
        pinkfab = findViewById<FloatingActionButton>(R.id.pinkfab)
        savebtn = findViewById<FloatingActionButton>(R.id.savebtn1)
        close = findViewById<FloatingActionButton>(R.id.close)
        datepicker1 = findViewById<TextView>(R.id.datepicker1)
        title = findViewById<TextView>(R.id.title1)
        descr = findViewById<TextView>(R.id.description1)
        phoneId = Settings.Secure.getString(this.contentResolver,Settings.Secure.ANDROID_ID)
        Log.d("manik",phoneId)

        cardview=findViewById<CardView>(R.id.cardview)

        colorChoose()
        datepicker1.setOnClickListener {

            val dialogfragment:DialogFragment = Datepicker()
            dialogfragment.show(supportFragmentManager,"date picker")

        }
        savebtn.setOnClickListener {
            //EditThing()

            if(title.text.toString().isEmpty() && descr.text.toString().isEmpty()){
                Toast.makeText(this,"Add some Text",Toast.LENGTH_SHORT).show()
            }
            else{
                DatebaseUploading()
            }


       }
        close.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            //set title for alert dialog
            builder.setTitle("Are you Sure want to Exit")
            //set message for alert dialog

            //performing positive action
            builder.setPositiveButton("Yes"){dialogInterface, which ->
                finish()
            }
            //performing negative action
            builder.setNegativeButton("No"){dialogInterface, which ->
                val alertDialog: AlertDialog = builder.create()
                // Set other dialog properties
                alertDialog.setCancelable(false)
                alertDialog.dismiss()

            }
            // Create the AlertDialog
            val alertDialog: AlertDialog = builder.create()
            // Set other dialog properties
            alertDialog.setCancelable(false)
            alertDialog.show()
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

         UserId = ref.push().key.toString()
        val user = User(UserId!!,t,d,date,c)
        ref.child(UserId).setValue(user).addOnCompleteListener {
            Toast.makeText(this,"Successfully saved",Toast.LENGTH_LONG).show()

            finish()

        }

    }

}

