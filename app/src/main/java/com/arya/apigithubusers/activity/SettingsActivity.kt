package com.arya.apigithubusers.activity

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import com.arya.apigithubusers.reminder.AlarmReceiver
import com.arya.apigithubusers.R
import com.arya.apigithubusers.local.SharedPref
import kotlinx.android.synthetic.main.activity_settings.*
import java.util.*

class SettingsActivity : AppCompatActivity() {

    private lateinit var sharedPref: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        setSupportActionBar(findViewById(R.id.toolbar_settings))
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.title = "Settings"
        }

        val calendar: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 9)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }

        tv_change_language.setOnClickListener {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }

        sharedPref = SharedPref(this)
        if (sharedPref.loadReminderState() == true) {
            reminder_switch.isChecked = true
        }

        reminder_switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                sharedPref.setReminderState(true)
                val intent = Intent(this, AlarmReceiver::class.java)
                val pendingIntent = PendingIntent.getBroadcast(this, 100, intent, 0)
                val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

                alarmManager.setInexactRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    AlarmManager.INTERVAL_DAY,
                    pendingIntent
                )

            } else {
                sharedPref.setReminderState(false)
                val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
                val intent = Intent(this, AlarmReceiver::class.java)
                val reqCode = 100
                val pendingIntent = PendingIntent.getBroadcast(this, reqCode, intent, 0)
                pendingIntent.cancel()
                alarmManager.cancel(pendingIntent)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
