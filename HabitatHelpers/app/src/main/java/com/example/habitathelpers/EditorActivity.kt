package com.example.habitathelpers

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import android.content.ClipData
import android.content.ClipDescription
import android.os.Build
import android.view.*
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.habitathelpers.databinding.ActivityEditorBinding
import kotlinx.android.synthetic.main.activity_editor.*
import kotlin.collections.MutableList


class EditorActivity : ActivityParent(), RecycleAdapter.MyItemClickListener{

    // Declare variables for pet and hab lists
    private lateinit var petList: MutableList<Pet>
    private lateinit var habList: MutableList<Hab>

    // Current selections when loading editor
    private lateinit var currPets: MutableList<Pet>
    private lateinit var currHab: Hab

    //drag and drop binding
    private lateinit var binding: ActivityEditorBinding

    //recycleadapter
    private lateinit var myAdapter: RecycleAdapter

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor)

        // use custom toolbar
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

        // TODO: get habitat components for recycler
        // temporary list to test functionality
        val testHab = mutableListOf<String>("Test 1", "Test 2", "Test 3")
        //recyclerview setup
        val rview = findViewById<RecyclerView>(R.id.rview)
        myAdapter=RecycleAdapter(this, myDB)
        //myAdapter.setMyItemClickListener(this)
        rview.adapter = myAdapter
        rview.layoutManager = LinearLayoutManager(this)


        //viewbinding
        binding = ActivityEditorBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //drag listeners
        //drag shadow listener for each object that can be dragged
        attachDragViewListener(textView)
        //drag event listener for editor view
        val EditorDragListener = View.OnDragListener { view, dragEvent ->

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
        //drag event listener for recyclerView
        val RecyclerDragListener = View.OnDragListener { view, dragEvent ->

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

        //bind to constraint layout
        binding.editorSpace.setOnDragListener(EditorDragListener)
        binding.recyclerSpace.setOnDragListener(RecyclerDragListener)
    }

    override fun onItemClick(view: View, position: Int) {
        // TODO: item interaction
    }
    fun onItemLongClicked(position: Int) {
        // TODO: drag and drop card item long clicked
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun attachDragViewListener(textView: TextView) {

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

    // TODO: determine why action bar and recyclerView ar not appearing

}