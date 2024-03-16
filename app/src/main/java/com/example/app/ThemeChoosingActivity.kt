package com.example.app

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity

class ThemeChoosingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_theme_choosing)
    }

    fun backButtonCallback(view: View){
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
        finish()
    }
}