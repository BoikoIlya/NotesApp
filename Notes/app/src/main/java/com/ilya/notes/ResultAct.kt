package com.ilya.notes

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.ilya.notes.Room.Note
import com.ilya.notes.Room.NoteViewModel
import com.ilya.notes.databinding.ActivityResultBinding
import java.util.*

class ResultAct : AppCompatActivity() {
    private lateinit var binding:ActivityResultBinding
    private lateinit var mNoteViewModel: NoteViewModel
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

           if(!intent.getStringExtra("edit").isNullOrEmpty())
          {binding.edName.setText(intent.getStringExtra("edit")) }


     // setTimeBtn
        binding.apply {
            switch1.setOnClickListener() {
                if (!binding.switch1.isChecked) {
                    date.visibility = View.GONE
                    time.visibility = View.GONE
                } else {
                    date.visibility = View.VISIBLE
                    time.visibility = View.VISIBLE
                }
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notification()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun click(view:View)=with(binding)
    {
        if(!edName.text.isEmpty()){
        mNoteViewModel = ViewModelProvider(this@ResultAct).get(NoteViewModel::class.java)
                insertDataToDB()

                if (switch1.isChecked) schaduleNotification()
            finish()
        }else edName.error = "Enter anything"
    }

fun insertDataToDB()
{
    val info = binding.edName.text.toString()
    val state = false
    val note = Note(0, info, state)

    mNoteViewModel.addNoteToDB(note)
}









    @RequiresApi(Build.VERSION_CODES.M)
    private fun schaduleNotification() {


    val intent = Intent(applicationContext, Notification::class.java)
        var ID = intent.getStringExtra("position")
        val title = "Remind"
        val message = binding.edName.text.toString()
        intent.putExtra(titleExtra, title)
        intent.putExtra(messageExtra, message)


        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            (0..100000).random(),
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = getTime()
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )
    }
    @SuppressLint("NewApi")
    private fun getTime(): Long {

        val minute = binding.time.minute
        val hour =binding.time.hour
        val day =binding.date.dayOfMonth
        val month =binding.date.month
        val year =binding.date.year

        val calendar = Calendar.getInstance()
        calendar.set(year, month, day, hour, minute)
        return calendar.timeInMillis
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun notification()
    {
        val name = "notif chanel"
        val desc = "DESCRIPTION"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelID, name, importance)
        channel.description = desc
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}
