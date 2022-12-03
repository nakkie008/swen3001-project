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
    var debugText : TextView? = null
    var countDownTimer : CountDownTimer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pomodoro2)
        pTimer = findViewById(R.id.pText)

        debugText = findViewById(R.id.printText)


        configureAlarmButton()
    }


    fun configureAlarmButton() {

        var btnClock : Button? = null
        var btn_Alarm : Button? = null

        btn_Alarm = findViewById(R.id.btnAlarm)


        btnClock = findViewById(R.id.btnClock_Pomodoro)

        countDownTimer?.cancel() //Cancel Timer before switching activity
        btn_Alarm.setOnClickListener {
            val intent = Intent(this@Pomodoro2, MainActivity::class.java)
            startActivity(intent)
        }



        btnClock.setOnClickListener {
            val intent = Intent(this@Pomodoro2, Clock::class.java)
            startActivity(intent)
        }

    }

    fun onClick(view: View){
        pTimer!!.text = ""
        var currentTime = Calendar.getInstance().time
        val endDateDay = "02/12/2022 16:40:00" //Ending date and time
        val format1 = SimpleDateFormat("dd/MM/yyyy hh:mm:ss", Locale.getDefault()) //Create pattern
        val endDate = format1.parse(endDateDay) //Formate ending date and time to have the pattern above

        val minutesInMilliSec115 = 6900000 //Holds total pomodoro time which is 115 minutes right now including 40 mins break time.
        endDate.time = currentTime.time + minutesInMilliSec115 //Add pomodoro time to current time

        val minutes5 : Long = 300000 //5 minutes break time in milli seconds
        val minutes30 : Long = 1800000 //30 minutes break time in milli seconds
        val minutes25: Long = minutes30 - minutes5


        val i = 0


        currentTime = Calendar.getInstance().time

        //milliseconds
        var different : Long = endDate.time - currentTime.time

        //debugText!!.text = "${endDate.time} endDAte ${currentTime.time} currentTime $different difference"

        CreateCountDownTimer(different)



    }

    fun CreateCountDownTimer(different: Long ){
        countDownTimer = object : CountDownTimer(different, 1000)
        {
            override fun onTick(millisUntilFinished: Long) {
                var diff = millisUntilFinished
                //Conversions
                val secondsInMilli: Long = 1000
                val minutesInMilli = secondsInMilli * 60
                val hoursInMilli = minutesInMilli * 60
                val daysInMilli = hoursInMilli * 24

                val elapsedDays = diff / daysInMilli
                diff %= daysInMilli //Trims decimals

                val elapsedHours : Long = diff / hoursInMilli
                diff %= hoursInMilli

                val elapsedMinutes : Long = diff / minutesInMilli
                diff %= minutesInMilli

                val elapsedSeconds = diff / secondsInMilli



                if (elapsedHours == 1.toLong() && elapsedSeconds in 25..30){

                    pTimer!!.text =  "BREAK TIME!!!"
                }
                else if (elapsedHours == 1.toLong() && elapsedSeconds in 0..5){
                    pTimer!!.text =  "BREAK TIME!!!"
                }
                else if(elapsedHours == 0.toLong() && elapsedSeconds in 30..35){
                    pTimer!!.text =  "BREAK TIME!!!"
                }
                else if(elapsedHours == 0.toLong() && elapsedSeconds in 0..5){
                    pTimer!!.text =  "Break!!!"
                }
                else{
                    pTimer!!.text =  "$elapsedHours hrs $elapsedMinutes mins $elapsedSeconds sec"
                }


            }

            override fun onFinish() {
                pTimer!!.text  = "Studying Done!"

            }
        }.start()
    }
}

