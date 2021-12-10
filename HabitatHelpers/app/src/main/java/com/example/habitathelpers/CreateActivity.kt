package com.example.habitathelpers

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_login.*
import java.util.ArrayList

class CreateActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{

    // Declare variables for pet and hab lists
    private lateinit var petList: MutableList<Pet>
    private lateinit var habList: MutableList<Hab>
    private var allSelected: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        allSelected = 0

        // use custom toolbar
        // TODO: additional toolbar functionality
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

        //populate spinners
        val spinOut = android.R.layout.simple_list_item_1
        //species
        val specSpinner: Spinner = findViewById(R.id.species_spinner)
        val specList = petList.map{it.species}
        // Create an ArrayAdapter using the string array and a default spinner layout
        val specArrayAdapter = ArrayAdapter(this, spinOut, specList)
        specSpinner.adapter = specArrayAdapter


        //gender
        val genSpinner: Spinner = findViewById(R.id.gen_spinner)
        val genList = arrayListOf("Male", "Female")
        // Create an ArrayAdapter using the string array and a default spinner layout
        val genArrayAdapter = ArrayAdapter(this, spinOut, genList)
        genSpinner.adapter = genArrayAdapter

        //age
        val ageSpinner: Spinner = findViewById(R.id.age_spinner)
        val ageList = arrayListOf(0, 1, 2)
        // Create an ArrayAdapter using the string array and a default spinner layout
        val ageArrayAdapter = ArrayAdapter(this, spinOut, ageList)
        ageSpinner.adapter = ageArrayAdapter

        //material
        val matSpinner: Spinner = findViewById(R.id.mat_spinner)
        val matList = arrayListOf("Plastic", "Glass")
            //habList.map{it.material}
        // Create an ArrayAdapter using the string array and a default spinner layout
        val matArrayAdapter = ArrayAdapter(this, spinOut, matList)
        matSpinner.adapter = matArrayAdapter

        //substrate
        val subSpinner: Spinner = findViewById(R.id.sub_spinner)
        val subList = arrayListOf("Pine", "Spruce")
            //habList.map{it.substrate}
        // Create an ArrayAdapter using the string array and a default spinner layout
        val subArrayAdapter = ArrayAdapter(this, spinOut, subList)
        subSpinner.adapter = subArrayAdapter

        //habitat dimensions
        val dimList = arrayListOf(0, 1, 2)
        val lenSpinner: Spinner = findViewById(R.id.len_spinner)
        val widSpinner: Spinner = findViewById(R.id.wid_spinner)
        val hgtSpinner: Spinner = findViewById(R.id.hgt_spinner)
        // Create an ArrayAdapter using the string array and a default spinner layout
        val dimArrayAdapter = ArrayAdapter(this, spinOut, dimList)
        lenSpinner.adapter = dimArrayAdapter
        widSpinner.adapter = dimArrayAdapter
        hgtSpinner.adapter = dimArrayAdapter


        // get reference to button
        val createButton = findViewById<Button>(R.id.button)


        // set on-click listener
        createButton.setOnClickListener {
            //if any items are empty, display error
            if ((specSpinner.getCount()==0) || (genSpinner.getCount()==0) || (ageSpinner.getCount()==0)
                || (matSpinner.getCount()==0) || (subSpinner.getCount()==0) || (lenSpinner.getCount()==0)
                || (widSpinner.getCount()==0) || (hgtSpinner.getCount()==0)){
                //error message
                val errMess = "Please make a selection for each option"
                // TODO: make button produce error if selections are missing
            }
            else {
                //pass current selections to newEditor function
                //petName
                val petSpec = specSpinner.selectedItem.toString()
                val petGen = genSpinner.selectedItem.toString()
                val petAge = ageSpinner.selectedItem.toString().toInt()
                //habName
                val habMat = matSpinner.selectedItem.toString()
                val habSub = subSpinner.selectedItem.toString()
                val habLen = lenSpinner.selectedItem.toString().toInt()
                val habWid = widSpinner.selectedItem.toString().toInt()
                val habHgt = hgtSpinner.selectedItem.toString().toInt()
                val passPet = mutableListOf<Pet>()
                passPet.add(
                    Pet(
                        // TODO: Finalize pet data class attributes, retrieve here
                        "name", petSpec, petGen, petAge
                    )
                )
                val passHab = Hab(
                    "name", habMat, habSub, habLen, habWid, habHgt
                )
                newEditor(passPet, passHab)
            }
        }

    }

    // TODO: style menu
    // TODO: Finalize pet attributes and update menu
    // TODO: add name input fields to menu
    // TODO: test with finalize database
    // change some spinners into text fields?

    private fun newEditor(passPet: MutableList<Pet>, passHab: Hab){
        // navigate to editorActivity
        val intent = Intent(this, EditorActivity::class.java).apply {
            //pass new pet as a 1-element list of Pet objects
            intent.putParcelableArrayListExtra("newPet", (passPet as ArrayList<out Pet>))
            //pass new hab
            intent.putExtra("newHab", passHab)
        }
        startActivity(intent)
    }

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