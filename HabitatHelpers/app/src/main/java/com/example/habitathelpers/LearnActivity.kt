package com.example.habitathelpers

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator
import kotlinx.android.synthetic.main.activity_load.mainAct
import kotlinx.android.synthetic.main.activity_load.navView
import kotlinx.android.synthetic.main.activity_load.toolbar


class LearnActivity : ActivityParent(), RecycleAdapter.MyItemClickListener {
    private lateinit var myAdapter: RecycleAdapter
    private lateinit var rview: RecyclerView

    var petImages = arrayListOf(R.drawable.python, R.drawable.leopardgecko, R.drawable.crestedgecko, R.drawable.beardeddragon)

    private var mTwoPane = false // check tablet or phone
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_learn)

        //check if tablet
        if (findViewById<LinearLayout>(R.id.linear3) == null){
            mTwoPane = true // Tablet layout is loaded
        }

        // use custom toolbar
        setSupportActionBar(toolbar)

        //navigation drawer setup
        val toggle = ActionBarDrawerToggle(this, mainAct, toolbar, R.string.open_nav, R.string.close_nav)
        mainAct.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)
        val myDB = DBHelper(this)

        //recyclerview setup
        rview = findViewById<RecyclerView>(R.id.rview)
        rview.layoutManager= LinearLayoutManager(this)
        myAdapter=RecycleAdapter(this, myDB)
        myAdapter.setMyItemClickListener(this)
        rview.itemAnimator = SlideInLeftAnimator()
        rview.adapter = AlphaInAnimationAdapter(myAdapter).apply {
            setDuration(1200)
            setFirstOnly(false)
        }
    }

    override fun onItemClick(view: View, position: Int) {
        //if tablet
        if (mTwoPane) {
            //open information fragment in Linear2
            supportFragmentManager.beginTransaction()
                .replace(R.id.linear2, LearnFragment(this, position))
                .addToBackStack("")
                .commit()
        }
        else{
            //load TYActivity with shared element
            val intent = Intent(this, YTActivity::class.java)
            intent.putExtra("position", position)
            intent.putExtra("picture", petImages[position])
            intent.putExtra("species", myAdapter.getPet(position))

            val options = ActivityOptions.makeSceneTransitionAnimation(this, view, "transition")
            // start the new activity
            startActivity(intent, options.toBundle())
        }
    }

    //navigation drawer presses, override for learn button
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_home-> {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("action", 0)
                startActivity(intent)
            }
            R.id.nav_create_new -> {
                val intent = Intent(this, CreateActivity::class.java)
                intent.putExtra("action", 0)
                startActivity(intent)
            }
            R.id.nav_load -> {
                val intent = Intent(this, LoadActivity::class.java)
                intent.putExtra("action", 0)
                startActivity(intent)
            }
            R.id.nav_learn -> {
                //do nothing
            }
        }
        mainAct.closeDrawer(GravityCompat.START)
        return true
    }
}