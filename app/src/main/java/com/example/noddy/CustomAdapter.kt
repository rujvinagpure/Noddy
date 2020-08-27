package com.example.noddy

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

//replace user
class CustomAdapter(val userList:ArrayList<User>): RecyclerView.Adapter<CustomAdapter.ViewHolder>()
{
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}