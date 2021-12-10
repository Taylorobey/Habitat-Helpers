package com.example.habitathelpers

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.example.habitathelpers.LoginFragment.OnLoginInteractionListener
import com.example.habitathelpers.MainFragment.OnMainInteractionListener
import com.example.habitathelpers.LoadFragment.OnLoadInteractionListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : ActivityParent(), OnLoginInteractionListener, OnMainInteractionListener, OnLoadInteractionListener {

    // TODO: CreateActivity implementation
    // TODO: LoginActivity implementation
    // TODO: EditorActivity implementation
    // TODO: LoadActivity implementation
    // TODO: RegActivity implementation

    private var subOpened: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // use custom toolbar
        setSupportActionBar(toolbar)

        //navigation drawer setup
        val toggle = ActionBarDrawerToggle(this, mainAct, toolbar, R.string.open_nav, R.string.close_nav)
        mainAct.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)

        // TODO: check if user is logged in
        //current default declaration so code runs
        //should be set to true if user is logged in
        var loggedIn = false

        //if not logged in, inflate fragment_login and get buttons
        if (!loggedIn){
            supportFragmentManager.beginTransaction().replace(R.id.linear2, LoginFragment())
                .addToBackStack("")
                .commit()
        }
        // if logged in, inflate fragment_main and get buttons
        else {
            supportFragmentManager.beginTransaction().replace(R.id.linear2, MainFragment())
                .addToBackStack("")
                .commit()
        }

    }

    //handle fragment button presses
    override fun onLoginInteraction(uri: View){
        // login button
        // navigate to LoginActivity
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
    override fun onRegisterInteraction(uri: View){
        // register button
        // navigate to RegActivity
        val intent = Intent(this, RegActivity::class.java)
        startActivity(intent)
    }
    override fun onCreateInteraction(uri: View){
        // create new habitat button
        // navigate to CreateActivity
        val intent = Intent(this, CreateActivity::class.java)
        startActivity(intent)
    }
    override fun onLoadInteraction(uri: View){
        // load habitat button
        // inflate LoadFragment
        subOpened = true
        supportFragmentManager.beginTransaction().replace(R.id.linear2, LoadFragment())
            .addToBackStack("")
            .commit()
    }

    //navigation drawer presses, override for home button
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
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
            }
        }
        mainAct.closeDrawer(GravityCompat.START)
        return true
    }

    //override to handle uninflating fragment
    override fun onBackPressed() {
        if(mainAct.isDrawerOpen(GravityCompat.START)){
            mainAct.closeDrawer(GravityCompat.START)
        }
        else if (subOpened){
            subOpened = false
            // use super once to bring back button menu in fragment
            super.onBackPressed()
        }
        else {
            //use super twice to uninflate fragment and then navigate to previous activity
            super.onBackPressed()
            super.onBackPressed()
        }
    }
}
