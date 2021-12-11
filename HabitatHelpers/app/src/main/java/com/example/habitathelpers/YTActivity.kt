package com.example.habitathelpers

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_yt.*
import kotlinx.android.synthetic.main.activity_yt.mainAct
import kotlinx.android.synthetic.main.activity_yt.navView
import kotlinx.android.synthetic.main.activity_yt.toolbar

class YTActivity : ActivityParent() {
    private var itemPos: Int = 0
    private var itemSpec: String = ""
    private var itemPic: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yt)

        val extras = intent.extras
        if (extras != null) {
            itemPos= extras.getInt("position")
            itemSpec= extras.getString("species").toString()
            itemPic = extras.getInt("picture")
        }

        //inflate with load habitat fragment
        supportFragmentManager.beginTransaction().replace(R.id.linear2, LearnFragment(this, itemPos))
            .addToBackStack("")
            .commit()

        // use custom toolbar
        setSupportActionBar(toolbar)

        //navigation drawer setup
        val toggle = ActionBarDrawerToggle(this, mainAct, toolbar, R.string.open_nav, R.string.close_nav)
        mainAct.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        //use super twice to uninflate fragment and then navigate to previous activity
        super.onBackPressed()
        super.onBackPressed()
    }
}