package com.example.habitathelpers

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import android.content.ClipData
import android.content.ClipDescription
import android.os.Build
import android.view.DragEvent
import android.view.View
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.habitathelpers.databinding.ActivityEditorBinding
import kotlinx.android.synthetic.main.activity_login.*

class EditorActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{

    // Declare variables for pet and hab lists
    private lateinit var petList: MutableList<Pet>
    private lateinit var habList: MutableList<Hab>

    // Current selections when loading editor
    private lateinit var currPets: MutableList<Pet>
    private lateinit var currHab: Hab

    //drag and drop binding
    private lateinit var binding: ActivityEditorBinding

    //drag event listener
    private val DragListener = View.OnDragListener { view, dragEvent ->

        //reference dragged item
        val draggableItem = dragEvent.localState as View

        //action responses
        when (dragEvent.action) {
            DragEvent.ACTION_DRAG_STARTED -> {
                true
            }
            DragEvent.ACTION_DRAG_ENTERED -> {
                binding.editorSpace.alpha = 0.3f
                true
            }
            DragEvent.ACTION_DRAG_LOCATION -> {
                true
            }
            DragEvent.ACTION_DRAG_EXITED -> {
                binding.editorSpace.alpha = 1.0f
                draggableItem.visibility = View.VISIBLE
                view.invalidate()
                true
            }
            DragEvent.ACTION_DROP -> {
                binding.editorSpace.alpha = 1.0f
                // position dropped
                val newX = dragEvent.x - (draggableItem.width / 2)
                val newY = dragEvent.y - (draggableItem.height / 2)
                val parent = draggableItem.parent as ConstraintLayout
                /*if (dragEvent.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                    val draggedData = dragEvent.clipData.getItemAt(0).text
                    //TODO : place dragged object in view
                }*/
                // if object would be placed entirely within new view
                val inView = true
                if (inView) {
                    //place where dropped
                    draggableItem.x = newX
                    draggableItem.y = newY
                    //remove from parent
                    parent.removeView(draggableItem)
                    //add to editor view
                    val dropArea = view as ConstraintLayout
                    dropArea.addView(draggableItem)
                }
                true
            }
            DragEvent.ACTION_DRAG_ENDED -> {
                draggableItem.visibility = View.VISIBLE
                view.invalidate()
                true
            }
            else -> {
                false
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.N)
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

        //viewbinding
        binding = ActivityEditorBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //drag listeners
        attachDragViewListener()
        //bind to imageview
        binding.editorSpace.setOnDragListener(DragListener)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun attachDragViewListener(){

        binding.textView.setOnLongClickListener { view: View ->

            val maskDragMessage = ""
            val item = ClipData.Item(maskDragMessage)

            val dataToDrag = ClipData(
                maskDragMessage,
                arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN),
                item
            )

            //generate shadow
            val maskShadow = Drag(view)
            view.startDragAndDrop(dataToDrag, maskShadow, view, 0)
            // make original item invisible
            view.visibility = View.INVISIBLE

            true
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