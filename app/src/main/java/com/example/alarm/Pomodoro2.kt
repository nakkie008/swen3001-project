package com.example.alarm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.os.CountDownTimer
import java.text.SimpleDateFormat
import java.util.*

class Pomodoro2 : AppCompatActivity() {

    var pTimer : TextView? = null
    var countDownTimer : CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pomodoro2)
        pTimer = findViewById(R.id.pText)


        configureAlarmButton()
    }


    fun configureAlarmButton() {
        var btn_Alarm: Button? = null
        btn_Alarm = findViewById(R.id.btnAlarm)

        countDownTimer?.cancel() //Cancel Timer before switching activity
        btn_Alarm.setOnClickListener {
            val intent = Intent(this@Pomodoro2, MainActivity::class.java)
            startActivity(intent)
        }

    }

    fun onClick(view: View){
        pTimer!!.text = ""
        val currentTime = Calendar.getInstance().time
        val endDateDay = "02/12/2022 05:00:00" //Ending date and time
        val format1 = SimpleDateFormat("dd/MM/yyyy hh:mm:ss", Locale.getDefault()) //Create pattern
        val endDate = format1.parse(endDateDay) //Formate ending date and time to have the pattern above

        //milliseconds
        var different = endDate.time - currentTime.time

        countDownTimer = object : CountDownTimer(different, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                var diff = millisUntilFinished
                //Conversions
                val secondsInMilli: Long = 1000
                val minutesInMilli = secondsInMilli * 60
                val hoursInMilli = minutesInMilli * 60
                val daysInMilli = hoursInMilli * 24

                val elapsedDays = diff / daysInMilli
                diff %= daysInMilli //Trims decimals

                val elapsedHours = diff / hoursInMilli
                diff %= hoursInMilli

                val elapsedMinutes = diff / minutesInMilli
                diff %= minutesInMilli

                val elapsedSeconds = diff / secondsInMilli

                pTimer!!.text =  "$elapsedDays days $elapsedHours hrs $elapsedMinutes mins $elapsedSeconds sec"

            }

            override fun onFinish() {
                pTimer!!.text  = "Studying Done!"
            }
        }.start()
    }
}

