package com.example.habitathelpers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.ScaleAnimation
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycle_item.view.*

class RecycleAdapter(private var dataSet: MutableList<String>) :
    RecyclerView.Adapter<RecycleAdapter.ViewHolder>() {

    var myListener: MyItemClickListener? = null
    interface MyItemClickListener { //interface – a type that will be derived from by the host activity
        fun onItemClick(view: View, position: Int) //interface function to be override by host activity
        fun onItemLongClicked(position: Int)
    }

    // MUST override
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view : View = inflater.inflate(R.layout.recycle_item, parent, false)

        // inflate item view
        return ViewHolder(view)
    }

    // MUST override
    override fun getItemCount(): Int {
        // number of items in the list
        return dataSet.size
    }

    // MUST override
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the contents of the view with that element
        // add code to replace elements in cardview with relevant data from dataSet

        viewHolder.name.text = dataSet[position]

        //setAnimation(viewHolder.name, position)
    }

    /*private fun setAnimation(view: View, position: Int){
        if(position != lastPosition){
            when(getItemViewType(position)){
                1 -> {
                    val animation = AnimationUtils.loadAnimation(view.context, android.R.anim.slide_in_left)
                    animation.duration = 700
                    animation.startOffset = position * 100L
                    view.startAnimation(animation)
                }
                2 -> {
                    val animation = AlphaAnimation(0.0f, 1.0f)
                    animation.duration = 1000
                    animation.startOffset = position * 50L
                    view.startAnimation(animation)
                }
                else -> {
                    val animation = ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
                    animation.duration = 500
                    animation.startOffset = position * 200L
                    view.startAnimation(animation)
                }
            }
            //animation.startOffset = position * 100L
            lastPosition = position
        }
    }*/

/*    override fun getItemViewType(position: Int): Int {
        return position % 3
    }*/


    fun addItem(str: String) {
        dataSet.add(str)
    }

    fun deleteItem(str: String) {
        for(i in 0 until dataSet.size)
            if(dataSet[i]==str) {
                dataSet.removeAt(i)
                notifyItemRemoved(i)
                break
            }
    }

    fun setMyItemClickListener(listener: EditorActivity) {
        this.myListener = listener
    }

    // static inner ViewHolder class
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val item = itemView.recycle_card
        val name = itemView.title_text

        init {
            // Define listeners for the ViewHolder's View.
            item.setOnClickListener {
                if(myListener != null){
                    if(adapterPosition != RecyclerView.NO_POSITION){
                        myListener!!.onItemClick(it, adapterPosition)
                    }
                }
            }
            item.setOnLongClickListener {
                if(myListener != null){
                    if(adapterPosition != RecyclerView.NO_POSITION){
                        myListener!!.onItemLongClicked(adapterPosition)
                    }
                }
                true
            }
        }
    }

}