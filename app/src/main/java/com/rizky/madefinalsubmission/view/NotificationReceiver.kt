package com.rizky.madefinalsubmission.view

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.rizky.madefinalsubmission.R
import com.rizky.madefinalsubmission.model.MovieResponse
import com.rizky.madefinalsubmission.network.ApiRepository
import com.rizky.madefinalsubmission.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class NotificationReceiver : BroadcastReceiver {

    companion object {
        private val EXTRA_TYPE: String? = "type"
        private val TYPE_DAILY: String? = "daily_reminder"
        private val TYPE_RELEASE: String? = "release_reminder"
        private const val ID_DAILY_REMINDER = 1000
        private const val ID_RELEASE_TODAY = 1001
    }

    private var context: Context? = null

    constructor()
    constructor(context: Context?) {
        this.context = context
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val type = intent?.getStringExtra(EXTRA_TYPE)
        if (type == TYPE_DAILY) {
            showDailyReminder(context)
        } else if (type == TYPE_RELEASE) {
            getReleaseToday(context)
        }
    }

    private fun getReminderTime(type: String?): Calendar? {
        val calendar = Calendar.getInstance()
        calendar[Calendar.HOUR_OF_DAY] = if (type == TYPE_DAILY) 7 else 8
        calendar[Calendar.MINUTE] = 0
        calendar[Calendar.SECOND] = 0
        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1)
        }
        return calendar
    }

    private fun getReminderIntent(type: String?): Intent? {
        val intent = Intent(context, NotificationReceiver::class.java)
        intent.putExtra(EXTRA_TYPE, type)
        return intent
    }

    fun setReleaseTodayReminder() {
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            ID_RELEASE_TODAY,
            getReminderIntent(TYPE_RELEASE),
            0
        )
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        getReminderTime(TYPE_RELEASE)?.timeInMillis?.let {
            alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP, it, AlarmManager.INTERVAL_DAY, pendingIntent
            )
        }
    }

    fun setDailyReminder() {
        val pendingIntent =
            PendingIntent.getBroadcast(context, ID_DAILY_REMINDER, getReminderIntent(TYPE_DAILY), 0)
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        getReminderTime(TYPE_DAILY)?.timeInMillis?.let {
            alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP, it, AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent
            )
        }
    }

    private fun getReleaseToday(context: Context?) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = Date()
        val now = dateFormat.format(date)
        val apiService = ApiRepository.getClient()?.create(ApiService::class.java)
        val call = apiService?.getReleasedMovies(now, now)
        call?.enqueue(object : Callback<MovieResponse?> {
            override fun onResponse(
                call: Call<MovieResponse?>?,
                response: Response<MovieResponse?>?
            ) {
                if (response != null) {
                    if (response.isSuccessful) {
                        val movies = response.body()?.results
                        var id = 2
                        if (movies != null) {
                            for (movie in movies) {
                                val title = movie.title
                                val desc =
                                    title + " " + context?.getString(R.string.release_reminder_message)
                                showReleaseToday(context, title, desc, id)
                                id++
                            }
                        }
                    }
                }
            }

            override fun onFailure(call: Call<MovieResponse?>?, t: Throwable?) {}
        })
    }

    private fun showReleaseToday(context: Context?, title: String?, desc: String?, id: Int) {
        val CHANNEL_ID = "Channel_2"
        val CHANNEL_NAME = "Today release channel"
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context, id, intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val uriRingtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val mNotificationManager =
            context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val mBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_movie_24)
            .setLargeIcon(
                BitmapFactory.decodeResource(
                    context.resources,
                    R.drawable.baseline_movie_24
                )
            )
            .setContentTitle(title)
            .setContentText(desc)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setSound(uriRingtone)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        val notification = mBuilder.build()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            mBuilder.setChannelId(CHANNEL_ID)
            mNotificationManager.createNotificationChannel(channel)
        }
        mNotificationManager.notify(id, notification)
    }

    private fun showDailyReminder(context: Context?) {
        val NOTIFICATION_ID = 1
        val CHANNEL_ID = "Channel_1"
        val CHANNEL_NAME = "Daily Reminder channel"
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context, NOTIFICATION_ID, intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val uriRingtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val mNotificationManager =
            context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val mBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_movie_24)
            .setLargeIcon(
                BitmapFactory.decodeResource(
                    context.resources,
                    R.drawable.baseline_movie_24
                )
            )
            .setContentTitle(context.resources?.getString(R.string.app_name))
            .setContentText(context.resources?.getString(R.string.daily_message_reminder))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setSound(uriRingtone)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        val notification = mBuilder.build()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            mBuilder.setChannelId(CHANNEL_ID)
            mNotificationManager.createNotificationChannel(channel)
        }
        mNotificationManager.notify(NOTIFICATION_ID, notification)
    }

    private fun cancelReminder(context: Context?, type: String?) {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, NotificationReceiver::class.java)
        val requestCode =
            if (type.equals(TYPE_DAILY, ignoreCase = true)) ID_DAILY_REMINDER else ID_RELEASE_TODAY
        val pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0)
        pendingIntent.cancel()
        alarmManager.cancel(pendingIntent)
    }

    fun cancelDailyReminder(context: Context?) {
        cancelReminder(context, TYPE_DAILY)
    }

    fun cancelReleaseToday(context: Context?) {
        cancelReminder(context, TYPE_RELEASE)
    }


}