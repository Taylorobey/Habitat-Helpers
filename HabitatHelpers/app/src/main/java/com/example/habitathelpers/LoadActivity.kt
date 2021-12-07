package com.example.habitathelpers

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.habitathelpers.LoadFragment.OnLoadInteractionListener
import kotlinx.android.synthetic.main.activity_main.*

class LoadActivity : AppCompatActivity(), OnLoadInteractionListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load)

        supportFragmentManager.beginTransaction().replace(R.id.linear, LoadFragment())
            .addToBackStack("")
            .commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        super.onBackPressed()
    }
}