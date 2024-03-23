package com.example.app

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import android.content.Intent

class MainAppPageActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_app_page)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    fun settingsButtonCallback(view: View){
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
        finish()
    }
}