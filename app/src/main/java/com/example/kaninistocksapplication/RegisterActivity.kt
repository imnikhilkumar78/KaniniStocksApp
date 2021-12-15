package com.example.kaninistocksapplication

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.Exception

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        var dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.loading_screen)
        dialog.setCanceledOnTouchOutside(false)


        val APP_PREFERENCES = "mysettings"
        var mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)


        val emailLayout = findViewById<TextInputLayout>(R.id.emailLayoutRegister)
        val passLayout = findViewById<TextInputLayout>(R.id.passwordLayoutRegister)

        val registerBtn = findViewById<TextView>(R.id.registerButton)

        val myApplication = application as stockAPI
        val httpApiService = myApplication.httpApiService

        registerBtn.setOnClickListener {
            dialog.show()


            val email = emailLayout.editText?.text.toString()
            val password = passLayout.editText?.text?.toString()

            var maskedEmail = if (email.isNullOrEmpty()) ""
            else email.lowercase()
            var maskedPass = if (password.isNullOrEmpty()) ""
            else password.lowercase()
            CoroutineScope(Dispatchers.IO).launch {
                val registerUser = User(maskedEmail, maskedPass)
                try {
                    httpApiService.register(registerUser)
                    val decodedJsonResult = httpApiService.login(registerUser)
                    withContext(Dispatchers.Main) {

                        var editor: SharedPreferences.Editor = mSettings.edit()
                        editor.putString("token", decodedJsonResult.token.toString()).apply()
                        editor.putString("email", decodedJsonResult.email.toString()).apply()
                        Toast.makeText(applicationContext, "Register OK", Toast.LENGTH_SHORT).show()
                        val intent = Intent(applicationContext, AllStocksList::class.java).apply {

                        }
                        dialog.dismiss()

                        startActivity(intent)
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {

                        Log.d("Register Exception: ", e.message.toString())
                        dialog.dismiss()

                        Toast.makeText(
                            applicationContext,
                            "Incorrect email or password",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }
}