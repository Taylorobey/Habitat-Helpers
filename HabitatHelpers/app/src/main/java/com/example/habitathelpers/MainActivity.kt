package com.example.habitathelpers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.example.habitathelpers.LoginFragment.OnLoginInteractionListener
import com.example.habitathelpers.MainFragment.OnMainInteractionListener
import com.example.habitathelpers.LoadFragment.OnLoadInteractionListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, OnLoginInteractionListener, OnMainInteractionListener, OnLoadInteractionListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // use custom toolbar
        // TODO: additional toolbar functionality
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(this, mainAct, toolbar, R.string.open_nav, R.string.close_nav)
        mainAct.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)

        // TODO: check if user is logged in
        //default declaration so code runs
        //should be set to true if user is logged in
        var logged_in = true

        //if not logged in, load fragment_login and get buttons
        if (!logged_in){
            supportFragmentManager.beginTransaction().replace(R.id.linear2, LoginFragment())
                .addToBackStack("")
                .commit()
        }
        // if logged in, load fragment_main and get buttons
        else {
            supportFragmentManager.beginTransaction().replace(R.id.linear2, MainFragment())
                .addToBackStack("")
                .commit()
        }

    }

    //handle fragment button presses
    override fun onLoginInteraction(uri: View){
        // login button
        // load LoginActivity
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
    override fun onRegisterInteraction(uri: View){
        // register button
        // load RegActivity
        val intent = Intent(this, RegActivity::class.java)
        startActivity(intent)
    }
    override fun onCreateInteraction(uri: View){
        // create new habitat button
        //load CreateActivity
        val intent = Intent(this, CreateActivity::class.java)
        startActivity(intent)
    }
    override fun onLoadInteraction(uri: View){
        // load habitat button
        // load LoadFragment
        supportFragmentManager.beginTransaction().replace(R.id.linear2, LoadFragment())
            .addToBackStack("")
            .commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_home-> {
                //do nothing

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
        }
        mainAct.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if(mainAct.isDrawerOpen(GravityCompat.START)){
            mainAct.closeDrawer(GravityCompat.START)
        }
        else {
            super.onBackPressed()
        }
    }
}
