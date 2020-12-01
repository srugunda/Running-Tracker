package com.solo.runtracker.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.solo.runtracker.R

class MainActivity : AppCompatActivity() {

    val runTrackerFragment = RunTrackerFragment()
    val runDetailsFragment = RunDetailsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, runTrackerFragment)
            commit()
        }
    }

    fun goToRun(view: View) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, runTrackerFragment)
            commit()
        }
    }
    fun viewRecentRuns(view: View) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, runDetailsFragment)
            commit()
        }
    }
}