package com.rizky.madefinalsubmission.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews
import com.rizky.madefinalsubmission.R
import com.rizky.madefinalsubmission.view.DetailActivity
import com.rizky.madefinalsubmission.view.MainActivity

/**
 * Implementation of App Widget functionality.
 */
class FavMovieWidget : AppWidgetProvider() {
    companion object{
        const val EXTRA_ITEM = "com.rizky.madefinalsubmission.EXTRA_ITEM"
        const val TOAST_ACTION = "com.rizky.madefinalsubmission.TOAST_ACTION"

        fun updateWidget(ctx: Context){
            val widgetManager = AppWidgetManager.getInstance(ctx)
            val widgetIds = widgetManager.getAppWidgetIds(ComponentName(ctx, FavMovieWidget::class.java))
            widgetManager.notifyAppWidgetViewDataChanged(widgetIds,
                R.id.stack_view
            )
        }

        internal fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            val intent = Intent(context, FavMovieService::class.java)
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME))

            val views = RemoteViews(context.packageName,
                R.layout.fav_movie_widget
            )
            views.setRemoteAdapter(R.id.stack_view, intent)
            views.setEmptyView(
                R.id.stack_view,
                R.id.empty_view
            )

            val toastIntent = Intent(context, FavMovieWidget::class.java)
            toastIntent.action =
                TOAST_ACTION
            toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME))
            val toastPendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent, PendingIntent.FLAG_UPDATE_CURRENT)
            views.setPendingIntentTemplate(R.id.stack_view, toastPendingIntent)

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(
                context,
                appWidgetManager,
                appWidgetId
            )
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        if (intent.action == AppWidgetManager.ACTION_APPWIDGET_UPDATE) {
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val thisWidget = ComponentName(context, FavMovieWidget::class.java)
            val appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget)
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.stack_view)
        }
    }
}
