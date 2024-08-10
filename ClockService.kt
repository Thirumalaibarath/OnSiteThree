package com.example.onsite_2

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ClockService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action)
        {
            Actions.START.toString() -> start()
            Actions.STOP.toString() -> stopSelf()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start()
    {
        CoroutineScope(Dispatchers.IO).launch{
            while(true)
            {
                val notification = NotificationCompat.Builder(this@ClockService ,"running channel")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("Clock")
                    .setContentText(getCurrentDateTime())
                    .build()

                Log.d("TIME", getCurrentDateTime())
                startForeground(1,notification)
                delay(1000L)
            }
        }

    }


    enum class Actions{
        START,STOP
    }

}

private fun getCurrentDateTime(): String {
    val now = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    return now.format(formatter)
}