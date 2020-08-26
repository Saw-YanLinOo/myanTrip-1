package com.vmyan.myantrip.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vmyan.myantrip.dao.TrainRecentDao
import com.vmyan.myantrip.model.train.TrainRecentItem
import kotlinx.coroutines.CoroutineScope

@Database(entities = [TrainRecentItem::class],version = 1)
abstract class TrainRecnetDB : RoomDatabase() {
    abstract fun trainRecentDao(): TrainRecentDao
    companion object {
        @Volatile
        private var INSTANCE: TrainRecnetDB? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): TrainRecnetDB {
            return INSTANCE ?: synchronized(this) {
                val instance =
                    Room.databaseBuilder(
                        context.applicationContext,
                        TrainRecnetDB::class.java,
                        "train_recentDB"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                INSTANCE = instance
                instance


            }
        }
    }
}