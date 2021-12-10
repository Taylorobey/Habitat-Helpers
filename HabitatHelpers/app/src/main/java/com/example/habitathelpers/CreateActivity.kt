package com.example.habitathelpers

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import kotlinx.android.synthetic.main.activity_login.*
import java.util.ArrayList

class CreateActivity : ActivityParent(){

    // Declare variables for pet and hab lists
    private lateinit var petList: MutableList<Pet>
    private lateinit var habList: MutableList<Hab>
    private lateinit var resPet: Pet
    private lateinit var resHab: Hab

    //spinners
    private lateinit var specSpinner: Spinner
    private lateinit var genSpinner: Spinner
    private lateinit var ageSpinner: Spinner
    private lateinit var matSpinner: Spinner
    private lateinit var subSpinner: Spinner
    private lateinit var lenSpinner: Spinner
    private lateinit var widSpinner: Spinner
    private lateinit var hgtSpinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        // use custom toolbar
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
        popSpin(petList, habList)

        //restore spinner selection on reorientation
        resSpin(petList, habList)

        // get reference to button
        val createButton = findViewById<Button>(R.id.button)

        // set on-click listener
        createButton.setOnClickListener {
            newCheck()
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

    private fun newCheck(){
        //if any items are empty/uninitialized, display error
        if (this::specSpinner.isInitialized && this::genSpinner.isInitialized && this::ageSpinner.isInitialized
            && this::matSpinner.isInitialized && this::subSpinner.isInitialized && this::lenSpinner.isInitialized
            && this::widSpinner.isInitialized && this::hgtSpinner.isInitialized){
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
        else {
            //error message
            val errMess = "Please make a selection for each option"
            Toast.makeText(applicationContext, errMess, Toast.LENGTH_SHORT).show()
        }
    }

    fun popSpin(petList: MutableList<Pet>, habList: MutableList<Hab>){
        //populate spinners
        val spinOut = android.R.layout.simple_list_item_1
        //species
        specSpinner = findViewById(R.id.species_spinner)
        val specList = petList.map{it.species}
        // Create an ArrayAdapter using the string array and a default spinner layout
        val specArrayAdapter = ArrayAdapter(this, spinOut, specList)
        specSpinner.adapter = specArrayAdapter

        //gender
        genSpinner = findViewById(R.id.gen_spinner)
        val genList = arrayListOf("Male", "Female")
        // Create an ArrayAdapter using the string array and a default spinner layout
        val genArrayAdapter = ArrayAdapter(this, spinOut, genList)
        genSpinner.adapter = genArrayAdapter

        //age
        ageSpinner = findViewById(R.id.age_spinner)
        val ageList = arrayListOf(0, 1, 2)
        // Create an ArrayAdapter using the string array and a default spinner layout
        val ageArrayAdapter = ArrayAdapter(this, spinOut, ageList)
        ageSpinner.adapter = ageArrayAdapter

        //material
        matSpinner = findViewById(R.id.mat_spinner)
        val matList = habList.map{it.material}
        // Create an ArrayAdapter using the string array and a default spinner layout
        val matArrayAdapter = ArrayAdapter(this, spinOut, matList)
        matSpinner.adapter = matArrayAdapter

        //substrate
        subSpinner = findViewById(R.id.sub_spinner)
        val subList = habList.map{it.substrate}
        // Create an ArrayAdapter using the string array and a default spinner layout
        val subArrayAdapter = ArrayAdapter(this, spinOut, subList)
        subSpinner.adapter = subArrayAdapter

        //habitat dimensions
        val dimList = arrayListOf(0, 1, 2)
        lenSpinner = findViewById(R.id.len_spinner)
        widSpinner = findViewById(R.id.wid_spinner)
        hgtSpinner = findViewById(R.id.hgt_spinner)
        // Create an ArrayAdapter using the string array and a default spinner layout
        val dimArrayAdapter = ArrayAdapter(this, spinOut, dimList)
        lenSpinner.adapter = dimArrayAdapter
        widSpinner.adapter = dimArrayAdapter
        hgtSpinner.adapter = dimArrayAdapter
    }

    fun resSpin(petList: MutableList<Pet>, habList: MutableList<Hab>){
        val specList = petList.map{it.species}
        val genList = arrayListOf("Male", "Female")
        val ageList = arrayListOf(0, 1, 2)
        val matList = habList.map{it.material}
        val subList = habList.map{it.substrate}
        val dimList = arrayListOf(0, 1, 2)
        //restore previous selections, if any
        if (this::resPet.isInitialized){
            if (resPet.species != "ERROR"){
                specSpinner.setSelection(specList.indexOf(resPet.species))
            }
            if (resPet.gender != "ERROR") {
                genSpinner.setSelection(genList.indexOf(resPet.gender))
            }
            if (resPet.age != -1) {
                ageSpinner.setSelection(ageList.indexOf(resPet.age))
            }
        }
        if (this::resHab.isInitialized){
            if (resHab.material != "ERROR"){
                matSpinner.setSelection(matList.indexOf(resHab.material))
            }
            if (resHab.substrate != "ERROR"){
                subSpinner.setSelection(subList.indexOf(resHab.substrate))
            }
            if (resHab.length != -1){
                lenSpinner.setSelection(dimList.indexOf(resHab.length))
            }
            if (resHab.width != -1){
                widSpinner.setSelection(dimList.indexOf(resHab.width))
            }
            if (resHab.height != -1){
                hgtSpinner.setSelection(dimList.indexOf(resHab.height))
            }
        }
    }

    //save and repopulate input on device reorientation
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        var passSpec = "ERROR"
        var passGen = "ERROR"
        var passAge = -1
        if (this::specSpinner.isInitialized){
            passSpec = specSpinner.selectedItem.toString()
        }
        if (this::genSpinner.isInitialized){
            passGen = genSpinner.selectedItem.toString()
        }
        if (this::ageSpinner.isInitialized){
            passAge = ageSpinner.selectedItem.toString().toInt()
        }
        outState.putParcelable("pet", Pet("name", passSpec, passGen, passAge))
        var passMat = "ERROR"
        var passSub = "ERROR"
        var passLen = -1
        var passWid = -1
        var passHgt = -1
        if (this::matSpinner.isInitialized){
            passMat = matSpinner.selectedItem.toString()
        }
        if (this::subSpinner.isInitialized){
            passSub = subSpinner.selectedItem.toString()
        }
        if (this::lenSpinner.isInitialized){
            passLen = lenSpinner.selectedItem.toString().toInt()
        }
        if (this::widSpinner.isInitialized){
            passWid = widSpinner.selectedItem.toString().toInt()
        }
        if (this::hgtSpinner.isInitialized){
            passHgt = hgtSpinner.selectedItem.toString().toInt()
        }
        outState.putParcelable("hab", Hab("name", passMat, passSub, passLen, passWid, passHgt))
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        resPet = savedInstanceState.getParcelable("pet")!!
        resHab = savedInstanceState.getParcelable("hab")!!
    }

    //navigation drawer presses, override for home button
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
}