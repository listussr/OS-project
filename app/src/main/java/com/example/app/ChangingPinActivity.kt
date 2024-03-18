package com.example.app

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.app.jsonClasses.SettingsJsonClass
import com.google.gson.Gson
import java.io.File
import java.io.IOException

class ChangingPinActivity : ComponentActivity() {

    private lateinit var settings: SharedPreferences
    private var index: Int = 0
    private var password = arrayOf(0, 0, 0, 0, 0)
    private val passwordEdit = R.id.passwordEdit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_changing_pin)
    }

    private fun changeActivity(){
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
        finish()
    }

    /*
    private fun savePasswordToSettings(strPassword: String) {
        val jsonString = applicationContext.assets.open("com/example/app/jsonClasses/resources/Settings.json").bufferedReader().use { it.readText() }
        val gson = Gson()
        val jsonObject = gson.fromJson(jsonString, SettingsJsonClass::class.java)
        jsonObject.appPassword = strPassword
        //val obj = SettingsJsonClass(jsonObject.wasRegistered, jsonObject.colorTheme, strPassword)
        val modifiedJsonString = gson.toJson(jsonObject)
        /*PrintWriter(FileWriter("C:\\University\\kurs_2\\semestr_2\\project\\app\\src\\main\\assets\\Settings.json")).use {
            val gson = Gson()
            val jsonString = gson.toJson(jsonObject)
            it.write(jsonString)
        }
         */
        try {
            File(applicationContext.filesDir, "Settings.json").readText()
        }catch(exception: IOException){
            Toast.makeText(applicationContext, "Can't open JSON file", Toast.LENGTH_LONG).show()
        }
    }

     */

    private fun savePasswordToSettings() {
        settings = this.getSharedPreferences(getString(R.string.name_sp_settings), Context.MODE_PRIVATE)
        val editor = settings.edit()
        Toast.makeText(applicationContext, "New password: ${passwordToString()}", Toast.LENGTH_LONG).show()
        editor.putString("Password", passwordToString())
        editor.commit()
    }

    private fun passwordToString(): String{
        var string = ""
        string += password[0].toString()
        string += password[1].toString()
        string += password[2].toString()
        string += password[3].toString()
        string += password[4].toString()
        return string
    }

    private fun saveNewPassword(){
        savePasswordToSettings()
        changeActivity()
    }

    /**
     * Метод для обработки нажатия кнопки 0
     */
    fun onZeroClicked(view: View) {
        val value: Int = 0
        password[index] = value
        val changingView = findViewById<TextView>(passwordEdit)
        val passwordEntered = changingView.text.toString()
        var amount = Integer.parseInt(passwordEntered)
        ++amount
        changingView.text = amount.toString()
        ++index
        if(index == 5){
            saveNewPassword()
        }
    }

    /**
     * Метод для обработки нажатия кнопки 1
     */
    fun onOneClicked(view: View) {
        val value: Int = 1
        password[index] = value
        val changingView = findViewById<TextView>(passwordEdit)
        val passwordEntered = changingView.text.toString()
        var amount = Integer.parseInt(passwordEntered)
        ++amount
        changingView.text = amount.toString()
        ++index
        if(index == 5){
            saveNewPassword()
        }
    }

    /**
     * Метод для обработки нажатия кнопки 2
     */
    fun onTwoClicked(view: View) {
        val value: Int = 2
        password[index] = value
        val changingView = findViewById<TextView>(passwordEdit)
        val passwordEntered = changingView.text.toString()
        var amount = Integer.parseInt(passwordEntered)
        ++amount
        changingView.text = amount.toString()
        ++index
        if(index == 5){
            saveNewPassword()
        }
    }

    /**
     * Метод для обработки нажатия кнопки 3
     */
    fun onThreeClicked(view: View) {
        val value: Int = 3
        password[index] = value
        val changingView = findViewById<TextView>(passwordEdit)
        val passwordEntered = changingView.text.toString()
        var amount = Integer.parseInt(passwordEntered)
        ++amount
        changingView.text = amount.toString()
        ++index
        if(index == 5){
            saveNewPassword()
        }
    }

    /**
     * Метод для обработки нажатия кнопки 4
     */
    fun onFourClicked(view: View) {
        val value: Int = 4
        password[index] = value
        val changingView = findViewById<TextView>(passwordEdit)
        val passwordEntered = changingView.text.toString()
        var amount = Integer.parseInt(passwordEntered)
        ++amount
        changingView.text = amount.toString()
        ++index
        if(index == 5){
            saveNewPassword()
        }
    }

    /**
     * Метод для обработки нажатия кнопки 5
     */
    fun onFiveClicked(view: View) {
        val value: Int = 5
        password[index] = value
        val changingView = findViewById<TextView>(passwordEdit)
        val passwordEntered = changingView.text.toString()
        var amount = Integer.parseInt(passwordEntered)
        ++amount
        changingView.text = amount.toString()
        ++index
        if(index == 5){
            saveNewPassword()
        }
    }

    /**
     * Метод для обработки нажатия кнопки 6
     */
    fun onSixClicked(view: View) {
        val value: Int = 6
        password[index] = value
        val changingView = findViewById<TextView>(passwordEdit)
        val passwordEntered = changingView.text.toString()
        var amount = Integer.parseInt(passwordEntered)
        ++amount
        changingView.text = amount.toString()
        ++index
        if(index == 5){
            saveNewPassword()
        }
    }

    /**
     * Метод для обработки нажатия кнопки 7
     */
    fun onSevenClicked(view: View) {
        val value: Int = 7
        password[index] = value
        val changingView = findViewById<TextView>(passwordEdit)
        val passwordEntered = changingView.text.toString()
        var amount = Integer.parseInt(passwordEntered)
        ++amount
        changingView.text = amount.toString()
        ++index
        if(index == 5){
            saveNewPassword()
        }
    }

    /**
     * Метод для обработки нажатия кнопки 8
     */
    fun onEightClicked(view: View) {
        val value: Int = 8
        password[index] = value
        val changingView = findViewById<TextView>(passwordEdit)
        val passwordEntered = changingView.text.toString()
        var amount = Integer.parseInt(passwordEntered)
        ++amount
        changingView.text = amount.toString()
        ++index
        if(index == 5){
            saveNewPassword()
        }
    }

    /**
     * Метод для обработки нажатия кнопки 9
     */
    fun onNineClicked(view: View) {
        val value: Int = 9
        password[index] = value
        val changingView = findViewById<TextView>(passwordEdit)
        val passwordEntered = changingView.text.toString()
        var amount = Integer.parseInt(passwordEntered)
        ++amount
        changingView.text = amount.toString()
        ++index
        if(index == 5){
            saveNewPassword()
        }
    }

    /**
     * Метод для обработки нажатия кнопки backspace
     */
    fun onBackSpaceClicked(view: View) {
        password[index] = -1
        if(index > 0) {
            val changingView = findViewById<TextView>(passwordEdit)
            val passwordEntered = changingView.text.toString()
            var amount = Integer.parseInt(passwordEntered)
            --amount
            changingView.text = amount.toString()
            --index
        }
    }
}