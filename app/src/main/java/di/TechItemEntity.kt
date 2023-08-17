package di

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tech_items")
data class TechItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val img: Int,
    val description: String
)