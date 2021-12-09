package com.example.habitathelpers

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_login.*

class EditorActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{

    // Declare variables for pet and hab lists
    private lateinit var petList: MutableList<Pet>
    private lateinit var habList: MutableList<Hab>

    // Current selections when loading editor
    private lateinit var currPets: MutableList<Pet>
    private lateinit var currHab: Hab

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor)

        // use custom toolbar
        // TODO: additional toolbar functionality
        // TODO: floating button for save function
        setSupportActionBar(toolbar)

        //navigation drawer setup
        val toggle = ActionBarDrawerToggle(this, mainAct, toolbar, R.string.open_nav, R.string.close_nav)
        mainAct.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)

        // DBHelper object
        val myDB = DBHelper(this)

        //get database data into local objects
        petList = myDB.getAllPets()
        habList = myDB.getAllHabs()

        // get intent from previous activity
        val extras = intent.extras
        if (extras != null) {
            currPets = extras.getParcelableArrayList<Pet>("newPet")!!
            currHab = extras.getParcelable<Hab>("newHab")!!
        }

    }



    // TODO: Implement editor
    //Habitat Editor
    //Animal information from database
    //Component information from database
    //Drag-and-drop interface
    //Alternate layout for tablets

    //navigation drawer presses
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