package di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideContext(): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideTechItemDatabase(context: Context): TechItemDatabase {
        return Room.databaseBuilder(
            context,
            TechItemDatabase::class.java,
            "tech_item_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideTechItemDao(appDatabase: TechItemDatabase): TechItemDao {
        return appDatabase.techItemDao()
    }

    // ...
}