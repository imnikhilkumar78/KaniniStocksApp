package com.example.kaninistocksapplication

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.util.*
import kotlin.concurrent.schedule

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        val APP_PREFERENCES = "mysettings"
        var mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        val token = mSettings.getString("token", "")
        val intent: Intent
        if (token == "")
            intent = Intent(applicationContext, MainActivity::class.java).apply { }
        else
            intent = Intent(applicationContext, AllStocksList::class.java).apply { }
        Timer("SettingUp", false).schedule(3000) {
            startActivity(intent)
            finish()

        }
    }
}