package com.example.clock

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.clock.databinding.StopwatchBinding
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import kotlin.math.roundToInt

class StopwatchActivity : AppCompatActivity() {
    private lateinit var binding : StopwatchBinding
    private lateinit var serviceIntent : Intent
    private var timerStarted = false
    private var time = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = StopwatchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // setContentView(R.layout.stopwatch)

        // val view = binding.root
        // setContentView(view)

        binding.btnStart.setOnClickListener { startStopTimer() }
        binding.btnReset.setOnClickListener { resetTimer() }

        serviceIntent = Intent(applicationContext, TimerService::class.java)
        registerReceiver(updateTime, IntentFilter(TimerService.TIMER_UPDATED))
    }

    private fun startStopTimer() {
        if(timerStarted)
            stopTimer()
        else
            startTimer()
    }

    private fun startTimer() {
        serviceIntent.putExtra(TimerService.TIME_EXTRA, time)
        startService(serviceIntent)
        binding.btnStart.text = getString(R.string.stop)
        //binding.btnStart.icon = getDrawable(R.drawable.ic_baseline_pause_24)
        timerStarted = true
    }

    private fun stopTimer() {
        stopService(serviceIntent)
        binding.btnStart.text = getString(R.string.start)
        //binding.btnStart.icon = getDrawable(R.drawable.ic_baseline_play_arrow_24)
        timerStarted = false
    }

    private fun resetTimer() {
        stopTimer()
        time = 0.0
        binding.txtTime.text = getTimeStringFromDouble(time)
    }

    private val updateTime : BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context : Context, intent : Intent) {
            time = intent.getDoubleExtra(TimerService.TIME_EXTRA, 0.0)
            binding.txtTime.text = getTimeStringFromDouble(time)
        }
    }

    private fun getTimeStringFromDouble(time : Double) : String {
        val resultInt = time.roundToInt()
        val hours = resultInt % 86400 / 3600
        val minutes = resultInt % 86400 % 3600 / 60
        val seconds = resultInt % 86400 % 3600 % 60

        return makeTimeString(hours, minutes, seconds)
    }

    private fun makeTimeString(hours : Int, minutes : Int, seconds : Int) : String = String.format("%02d:%02d:%02d", hours, minutes, seconds)
}