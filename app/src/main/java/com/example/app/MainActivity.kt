package com.example.app

import android.os.Bundle
import android.view.View
import android.content.Context
import androidx.activity.ComponentActivity
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : ComponentActivity() {

    /**
     * Пароль приложения из настроек
     */
    private var appPassword: Array<Int> = arrayOf(1, 2, 3, 4, 5)

    /**
     * Файл с настройками приложения
     */
    private lateinit var settings: SharedPreferences

    /**
     * Массив для набираемого пароля
     */
    private var password: Array<Int> = arrayOf(0, 0, 0, 0, 0)

    /**
     * Индекс в массиве для вводимого символа
     */
    private var index: Int = 0

    /**
     * id виджета для изменения отображения количества введеных цифр пароля
     */
    private val passwordEdit = R.id.passwordEdit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pin_code_entering)
        settings = getSharedPreferences(getString(R.string.name_sp_settings), Context.MODE_PRIVATE)
        setColorTheme(getColorTheme())
        appPassword = unmaskPassword(getAppPassword())

    }

    /**
     * Функция получения цветовой темы приложения из SharedPreferences
     * @return lightThemeFlag
     */
    private fun getColorTheme() : Boolean {
        val lightThemeFlag: Boolean
        if(settings.contains("ColorTheme")){
            lightThemeFlag = settings.getBoolean("ColorTheme", true)
        }
        else{
            lightThemeFlag = true
            val editor = settings.edit()
            editor.putBoolean("ColorTheme", lightThemeFlag)
            editor.commit()
        }
        Toast.makeText(applicationContext, "Light theme, $lightThemeFlag", Toast.LENGTH_LONG).show()
        return lightThemeFlag
    }

    /**
     * Функция установки цветовой темы, исходя из установленных настроек
     * @param colorTheme
     */
    private fun setColorTheme(colorTheme: Boolean) {
        val mainLayout = findViewById<LinearLayout>(R.id.main_layout)
        if(colorTheme) {
            mainLayout.setBackgroundColor(Color.WHITE)
        } else {
            mainLayout.setBackgroundColor(Color.GRAY)
        }
    }

    /**
     * Получаем пароль из SharedPreferences
     * @return String? strPassword
     */
    private fun getAppPassword(): String? {
        val strPassword: String?
        if(settings.contains("Password")){
            strPassword = settings.getString("Password", null)
        }
        else{
            strPassword = "00000"
            val editor = settings.edit()
            editor.putString("Password", strPassword)
            editor.commit()
        }
        Toast.makeText(applicationContext, "Password, $strPassword", Toast.LENGTH_LONG).show()
        return strPassword
    }

    private fun getJSONFromAssets(): String? {

        var json: String? = null
        val charset: Charset = Charsets.UTF_8
        try {
            val myUsersJSONFile = assets.open("com/example/app/jsonClasses/resources/Settings.json")
            val size = myUsersJSONFile.available()
            val buffer = ByteArray(size)
            myUsersJSONFile.read(buffer)
            myUsersJSONFile.close()
            json = String(buffer, charset)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }

    /**
     * Получаем численное значение пароля из строки
     * @param strPassword
     * @return Array<Int>
     */
    private fun unmaskPassword(strPassword: String?): Array<Int> {
        return arrayOf(
            strPassword!![0].toString().toInt(),
            strPassword[1].toString().toInt(),
            strPassword[2].toString().toInt(),
            strPassword[3].toString().toInt(),
            strPassword[4].toString().toInt()
            )
    }

    /**
     * Очищаем пароль, устанавливаем индекс в 0 и выводим это на экран
     */
    private fun clearPassword(){
        index = 0
        for (i in 0..4){
            password[i] = 0
        }
        val changingView = findViewById<TextView>(passwordEdit)
        val amount = 0
        changingView.text = amount.toString()
    }


    /**
     * Метод для переключения между страницами
     */
    private fun changeActivity() {
        clearPassword()
        val intent = Intent(this@MainActivity, MainAppPage::class.java)
        startActivity(intent)
        finish()
    }

    /**
     * Метод для проверки корректности пароля
     */
    private fun comparePasswords(view: View){
        if(password contentEquals appPassword){
            changeActivity()
        }
        else {
            clearPassword()
            Toast.makeText(applicationContext, R.string.msg_incorrect_password, Toast.LENGTH_LONG).show()
        }
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
            comparePasswords(view)
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
            comparePasswords(view)
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
            comparePasswords(view)
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
            comparePasswords(view)
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
            comparePasswords(view)
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
            comparePasswords(view)
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
            comparePasswords(view)
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
            comparePasswords(view)
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
            comparePasswords(view)
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
            comparePasswords(view)
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
