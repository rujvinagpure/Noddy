package com.example.noddy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    lateinit var recylerview: RecyclerView
    lateinit var add:FloatingActionButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        add = findViewById(R.id.addbtn)

        add.setOnClickListener {
            val intent = Intent(this,EditActivity::class.java)
            startActivity(intent)
        }

        //list = mutableListOf()
        recylerview = findViewById(R.id.recyclerView)

        recylerview.setLayoutManager(GridLayoutManager(this, 2))

        val phoneId = Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)

        val ref = FirebaseDatabase.getInstance().getReference("$phoneId")


        val option = FirebaseRecyclerOptions.Builder<User>()
            .setQuery(ref, User::class.java)
            .build()

        class ItemViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

            var title: TextView = itemView!!.findViewById(R.id.titleCard)
             var desrc: TextView = itemView!!.findViewById(R.id.descrCard)
            var card:CardView = itemView!!.findViewById(R.id.cardcard)

            var onClickedListener: ((position: Int, descr: String) -> Unit)? = null

            fun bindView(position: Int) {

              //  itemView.setOnClickListener{
                  //  onClickedListener?.invoke(position, desrc.text.toString())
                //}
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

                //item.bindView(-position)

                ref.child(itemId).addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {}

                    override fun onDataChange(p0: DataSnapshot) {
                        item.title.setText( model.title)
                        item.desrc.setText(model.description)
                        item.card.setCardBackgroundColor(model.color)

                    }
                })
            }
        }
        recylerview.adapter = myfirebaseRecyclerViewAdapter
        myfirebaseRecyclerViewAdapter.startListening()

    }
}




