package com.example.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import android.content.Intent

class SettingsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
    }

    fun backButtonCallback(view: View) {
        val intent = Intent(this, MainAppPage::class.java)
        startActivity(intent)
        finish()
    }

    fun changeThemeButtonCallback(view: View) {
        val intent = Intent(this, ThemeChoosingActivity::class.java)
        startActivity(intent)
    }

    fun changePinCodeButtonCallback(view: View){
        val intent = Intent(this, ChangingPinActivity::class.java)
        startActivity(intent)
    }


}