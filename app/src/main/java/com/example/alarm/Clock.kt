package com.example.alarm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Clock : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clock)

        configureButtons()
    }

    fun configureButtons() {
        var btnPomodoroClock: Button? = null
        btnPomodoroClock = findViewById(R.id.btnPomodoro_Clock)

        var btnAlarmClock: Button? = null
        btnAlarmClock = findViewById(R.id.btnAlarm_Clock)

        btnPomodoroClock.setOnClickListener {
            val intent = Intent(this@Clock, Pomodoro2::class.java)
            startActivity(intent)
        }

        btnAlarmClock.setOnClickListener {
            val intent = Intent(this@Clock, MainActivity::class.java)
            startActivity(intent)
        }
    }
}