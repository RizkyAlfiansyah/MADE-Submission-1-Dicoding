package com.rizky.madefinalsubmission.widget

import android.content.Intent
import android.widget.RemoteViewsService

class FavMovieService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory =
        StackRemoteViewsFactory(this.applicationContext)
}