package di

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(mainActivity: MainActivity)
    fun injectDependency(dependency: Dependency)

    fun techItemDao(): TechItemDao
    fun techItemDatabase(): TechItemDatabase // Метод для получения экземпляра базы данных

}