package di

import dagger.Module
import dagger.Provides

@Module
class AppModule {
    @Provides
    fun provideDependency(): Dependency {
        return Dependency()
    }
}