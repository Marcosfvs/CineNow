package com.devspacecinenow

import android.app.Application
import androidx.room.Room
import com.devspacecinenow.common.data.local.CineNowDataBase

class CineNowApplication: Application() {

    val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            CineNowDataBase::class.java, "database-cine-now"
        ).build()
    }
}