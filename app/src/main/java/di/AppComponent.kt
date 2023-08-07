package di

import dagger.Component

@Component(modules = [Dependency::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun injectDependency(dependency: Dependency)

    @Component.Builder
    interface Builder {
        fun build(): AppComponent
    }
}