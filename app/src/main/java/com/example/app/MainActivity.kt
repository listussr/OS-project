package com.example.app

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.app.ui.theme.AppTheme
import android.content.Intent
import android.widget.ImageView
import android.widget.TextView

class MainActivity : ComponentActivity() {

    private val appPassword = arrayOf(1, 2, 3, 4, 5)

    private var password = arrayOf(0, 0, 0, 0, 0)
    private var index: Int = 0

    private val passwordEdit = R.id.passwordEdit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }

    /**
     * Метод для переключения между страницами
     */
    private fun changeActivity() {
        val intent = Intent(this@MainActivity, MainAppPage::class.java)
        startActivity(intent)
    }

    /**
     * Метод для проверки корректности пароля
     */
    private fun comparePasswords(view: View){
        if(password contentEquals appPassword){
            changeActivity()
        }
        else{
            for (i in 0..4){
                password[i] = 0
            }
            var changingView = findViewById<TextView>(passwordEdit)
            var amount = 0
            changingView.text = amount.toString()
            index = 0
        }
    }

    /**
     * Метод для обработки нажатия кнопки 0
     */
    fun onZeroClicked(view: View) {
        val value: Int = 0
        password[index] = value
        var changingView = findViewById<TextView>(passwordEdit)
        var passwordEntered = changingView.text.toString()
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
        var changingView = findViewById<TextView>(passwordEdit)
        var passwordEntered = changingView.text.toString()
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
        var changingView = findViewById<TextView>(passwordEdit)
        var passwordEntered = changingView.text.toString()
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
        var changingView = findViewById<TextView>(passwordEdit)
        var passwordEntered = changingView.text.toString()
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
        var changingView = findViewById<TextView>(passwordEdit)
        var passwordEntered = changingView.text.toString()
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
        var changingView = findViewById<TextView>(passwordEdit)
        var passwordEntered = changingView.text.toString()
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
        var changingView = findViewById<TextView>(passwordEdit)
        var passwordEntered = changingView.text.toString()
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
        var changingView = findViewById<TextView>(passwordEdit)
        var passwordEntered = changingView.text.toString()
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
        var changingView = findViewById<TextView>(passwordEdit)
        var passwordEntered = changingView.text.toString()
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
        var changingView = findViewById<TextView>(passwordEdit)
        var passwordEntered = changingView.text.toString()
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
        var changingView = findViewById<TextView>(passwordEdit)
        var passwordEntered = changingView.text.toString()
        var amount = Integer.parseInt(passwordEntered)
        --amount
        changingView.text = amount.toString()
        --index
        }
    }
}
