package com.example.habitathelpers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.ScaleAnimation
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycle_item.view.*
import android.content.Context
import android.graphics.drawable.Drawable
import androidx.cardview.widget.CardView
import com.squareup.picasso.Picasso

class RecycleAdapter(context:Context, private var db: DBHelper) :
    RecyclerView.Adapter<RecycleAdapter.ViewHolder>() {

    //create data from database
    var petSet = db.getAllPets()

    var petImages = arrayListOf(R.drawable.python, R.drawable.leopardgecko, R.drawable.crestedgecko, R.drawable.beardeddragon)

    var myListener: MyItemClickListener? = null
    interface MyItemClickListener { //interface – a type that will be derived from by the host activity
        fun onItemClick(view: View, position: Int) //interface function to be override by host activity
    }

    var lastPosition = - 1

    // MUST override
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view : View
        view = inflater.inflate(R.layout.recycle_item, parent, false)

        // inflate item view
        return ViewHolder(view)
    }

    // MUST override
    override fun getItemCount(): Int {
        // number of items in the list
        return db.getPetSize()
    }

    // MUST override
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the contents of the view with that element
        val currPet = petSet[position]
        viewHolder.name.text = currPet.species
        viewHolder.picture.setImageResource(petImages[position])
        setAnimation(viewHolder.picture, position)
    }

    private fun setAnimation(view: View, position: Int){
        val animation = AnimationUtils.loadAnimation(view.context, android.R.anim.slide_in_left)
        animation.duration = 700
        animation.startOffset = position * 100L
        view.startAnimation(animation)

        lastPosition = position
    }

    fun getPet(i: Int): Pet {
        return petSet[i]
    }

    fun getPets(): MutableList<Pet> {
        return petSet
    }

    fun setMyItemClickListener(listener: LearnActivity) {
        this.myListener = listener
    }

    fun findPet(query: String): Int {
        var quer_pos = -1
        var i = 0
        var found = false
        //find first movie that contains query string
        while ((i < petSet.size) && !found){
            if (petSet[i].species.contains(query)){
                quer_pos = i
            }
            i += 1
        }
        return quer_pos
    }

    // static inner ViewHolder class
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val petView = itemView.recycle_card
        val picture = itemView.pet_image
        val name = itemView.name_text

        init {
            // Define listeners for the ViewHolder's View.
            petView.setOnClickListener {
                if(myListener != null){
                    if(adapterPosition != RecyclerView.NO_POSITION){
                        myListener!!.onItemClick(it, adapterPosition)
                    }
                }
            }
        }
        fun getView(): CardView? {
            return petView
        }
    }

}