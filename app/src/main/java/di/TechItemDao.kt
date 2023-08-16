package di

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TechItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTechItem(techItem: TechItemEntity)

    @Query("SELECT * FROM tech_items")
    suspend fun getAllTechItems(): List<TechItemEntity>
}