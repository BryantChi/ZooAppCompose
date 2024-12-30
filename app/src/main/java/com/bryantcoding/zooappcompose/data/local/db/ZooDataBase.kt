package com.bryantcoding.zooappcompose.data.local.db

import android.content.Context
import android.util.Log
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.bryantcoding.zooappcompose.data.local.entities.AnimalEntity
import com.bryantcoding.zooappcompose.data.local.entities.ZooAreaEntity

@Database(
    entities = [ZooAreaEntity::class, AnimalEntity::class],
    version = 2,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 1, to = 2)
    ]
)
abstract class ZooDatabase : RoomDatabase() {
    abstract fun zooDao(): ZooDao

    companion object {

        fun getDatabase(context: Context): ZooDatabase {
            return Room.databaseBuilder(
                    context.applicationContext,
                    ZooDatabase::class.java,
                    "zoo_database"
                ).addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        Log.d("ZooDatabase", "Room Database Created")
                    }

                    override fun onOpen(db: SupportSQLiteDatabase) {
                        super.onOpen(db)
                        Log.d("ZooDatabase", "Room Database Opened")
                    }
                }).fallbackToDestructiveMigration().build()
        }
    }
}