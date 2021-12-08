package com.example.habitathelpers

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_login.*

class CreateActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        // use custom toolbar
        // TODO: additional toolbar functionality
        setSupportActionBar(toolbar)

        //navigation drawer setup
        val toggle = ActionBarDrawerToggle(this, mainAct, toolbar, R.string.open_nav, R.string.close_nav)
        mainAct.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)
    }

    // TODO: menu to select habitat creation criteria

    // TODO: pass selections to editor activity

    //navigation drawer presses
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_home-> {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("action", 0)
                startActivity(intent)
            }
            R.id.nav_create_new -> {
                //do nothing
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