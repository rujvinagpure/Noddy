package com.example.noddy

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var recylerview: RecyclerView
    lateinit var add:FloatingActionButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_main)

        add = findViewById(R.id.addbtn)

        add.setOnClickListener {
            val intent = Intent(this,EditActivity::class.java)
            startActivity(intent)
        }

        //list = mutableListOf()
        recylerview = findViewById(R.id.recyclerView)

        val linearLayoutManager = GridLayoutManager(this,2,LinearLayoutManager.VERTICAL,false)
            // linearLayoutManager.reverseLayout = true
        //linearLayoutManager.stackFromEnd = true
        recylerview.setLayoutManager(linearLayoutManager)

        //recylerview.setLayoutManager(GridLayoutManager(this, 2))

        val phoneId = Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)

        val ref = FirebaseDatabase.getInstance().getReference("$phoneId")


        val option = FirebaseRecyclerOptions.Builder<User>()
            .setQuery(ref, User::class.java)
            .build()

        class ItemViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

            var title: TextView = itemView!!.findViewById(R.id.titleCard)
             var desrc: TextView = itemView!!.findViewById(R.id.descrCard)
            var card:CardView = itemView!!.findViewById(R.id.cardcard)
            var date:TextView = itemView!!.findViewById(R.id.datenotshow)
            var userId:TextView = itemView!!.findViewById(R.id.userIdnotshow)
           var close: ImageButton = itemView!!.findViewById(R.id.closebtn)




            var onClickedListener: ((position: Int, descr: String) -> Unit)? = null

            fun bindView(position: Int) {

               itemView.setOnClickListener{
                    onClickedListener?.invoke(position, desrc.text.toString())
                   Log.d("mannik", userId.text.toString())
                   val intent = Intent(applicationContext,ChangeActivity::class.java)
                       .putExtra("title",title.text.toString())
                       .putExtra("descr",desrc.text.toString())
                       .putExtra("Userid",userId.text.toString())
                       .putExtra("Date",date.text.toString())
                       .putExtra("color",card.cardBackgroundColor.defaultColor.toString())

                   startActivity(intent)

                   close.setOnClickListener {
                       Log.d("mmm",userId.text.toString())
                       val ref = FirebaseDatabase.getInstance().getReference("$phoneId").child(userId.text.toString())
                       ref.removeValue()
                   }


                }
            }


        }

        val myfirebaseRecyclerViewAdapter = object : FirebaseRecyclerAdapter<User, ItemViewHolder>(option) {





                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

                    val itemView = LayoutInflater.from(this@MainActivity)
                        .inflate(R.layout.particular_item, parent, false)
                    return ItemViewHolder(itemView)
                }


            override fun onBindViewHolder(item: ItemViewHolder, position: Int, model: User) {
                val itemId = getRef(position).key.toString()

                item.bindView(position)

                ref.child(itemId).addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {}

                    override fun onDataChange(p0: DataSnapshot) {
                        item.title.setText( model.title)
                        item.desrc.setText(model.description)
                        item.card.setCardBackgroundColor(model.color)
                        item.date.setText(model.date)
                        item.userId.setText(model.userId)

                    }
                })
            }
        }

        myfirebaseRecyclerViewAdapter.notifyDataSetChanged()

        recylerview.adapter = myfirebaseRecyclerViewAdapter


        myfirebaseRecyclerViewAdapter.startListening()


    }
}




