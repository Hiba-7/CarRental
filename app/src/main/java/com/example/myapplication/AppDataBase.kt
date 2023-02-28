package com.example.myapplication

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.DataBase.Trajet
import com.example.myapplication.DataBase.TrajetDao
import androidx.sqlite.db.SupportSQLiteDatabase

import androidx.room.migration.Migration




@Database(entities = [Trajet::class],version = 3)
abstract class AppDataBase :RoomDatabase()
{
    abstract fun getTrajetDao(): TrajetDao
}

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE trajets ADD COLUMN CarModele TEXT")
        database.execSQL("ALTER TABLE trajets ADD COLUMN CarMarque TEXT")
    }
}
