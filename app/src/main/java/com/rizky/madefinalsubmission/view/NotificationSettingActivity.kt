package com.rizky.madefinalsubmission.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.appcompat.widget.Toolbar
import butterknife.BindView
import butterknife.ButterKnife
import com.rizky.madefinalsubmission.BuildConfig.MY_SHARED_PREFERENCES
import com.rizky.madefinalsubmission.R

class NotificationSettingActivity : AppCompatActivity() {
    @BindView(R.id.daily_switch)
    @JvmField
    var dailyReminderSwitch: SwitchCompat? = null
    @BindView(R.id.release_switch)
    @JvmField
    var releaseReminderSwitch: SwitchCompat? = null
    @BindView(R.id.toolbar_notif)
    @JvmField
    var toolbar: Toolbar? = null
    private var sharedPreferences: SharedPreferences? = null
    private var sharedPreferenceEdit: SharedPreferences.Editor? = null
    private var notificationReceiver: NotificationReceiver? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_setting)
        sharedPreferences = getSharedPreferences(MY_SHARED_PREFERENCES, Context.MODE_PRIVATE)
        notificationReceiver = NotificationReceiver(this)
        ButterKnife.bind(this)
        initToolbar()
        listenSwitchChanged()
        setPreferences()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        setTitle(R.string.notification_setting_title)
    }

    private fun listenSwitchChanged() {
        dailyReminderSwitch?.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferenceEdit = sharedPreferences?.edit()
            sharedPreferenceEdit?.putBoolean("daily_reminder", isChecked)
            sharedPreferenceEdit?.apply()
            if (isChecked) {
                notificationReceiver?.setDailyReminder()
            } else {
                notificationReceiver?.cancelDailyReminder(applicationContext)
            }
        }
        releaseReminderSwitch?.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferenceEdit = sharedPreferences?.edit()
            sharedPreferenceEdit?.putBoolean("release_reminder", isChecked)
            sharedPreferenceEdit?.apply()
            if (isChecked) {
                notificationReceiver?.setReleaseTodayReminder()
            } else {
                notificationReceiver?.cancelReleaseToday(applicationContext)
            }
        }
    }

    private fun setPreferences() {
        val dailyReminder = sharedPreferences?.getBoolean("daily_reminder", false)
        val releaseReminder = sharedPreferences?.getBoolean("release_reminder", false)
        if (dailyReminder != null) {
            dailyReminderSwitch?.isChecked = dailyReminder
        }
        if (releaseReminder != null) {
            releaseReminderSwitch?.isChecked = releaseReminder
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) finish()
        return true
    }

}
