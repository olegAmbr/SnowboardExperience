package di

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TechItemEntity::class], version = 1)
abstract class TechItemDatabase : RoomDatabase() {
    abstract fun techItemDao(): TechItemDao
}