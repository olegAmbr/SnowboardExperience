package di

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.snowboardexperience.R
import com.example.snowboardexperience.databinding.ActivityMainBinding
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appComponent: AppComponent

    @Inject
    lateinit var dependency: Dependency

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Инициализация Dagger компонента для активити
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(applicationContext)) // Передаем ApplicationContext
            .build()

        appComponent.inject(this)

        // Установить Toolbar как ActionBar
        setSupportActionBar(binding.topAppBarMenu)

        // Установить собственное название для активити
        supportActionBar?.title = "Snow"

        if (savedInstanceState == null) {
            // При первом создании активности добавляем MainFragment в контейнер
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, HomeFragment())
                .commit()
        }

        // Обработчик для нижней навигационной панели
        binding.bottomNavigationMenu.setOnNavigationItemSelectedListener { menuItem ->
            val tag = when (menuItem.itemId) {
                R.id.home -> "home"
                R.id.favorites -> "favorites"
                R.id.settings -> "settings"
                else -> return@setOnNavigationItemSelectedListener false
            }

            val fragment = checkFragmentExistence(tag)
            changeFragment(fragment ?: createFragment(tag), tag)
            true
        }
    }

    // Создание фрагмента по тегу
    private fun createFragment(tag: String): Fragment {
        return when (tag) {
            "home" -> HomeFragment()
            "favorites" -> FavoritesFragment()
            "settings" -> SettingsFragment()
            else -> throw IllegalArgumentException("Неизвестный тег фрагмента: $tag")
        }
    }

    // Замена текущего фрагмента новым и добавление в стек возврата
    private fun changeFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment, tag)
            .addToBackStack(null)
            .commit()
    }

    // Проверка наличия фрагмента с заданным тегом
    private fun checkFragmentExistence(tag: String): Fragment? =
        supportFragmentManager.findFragmentByTag(tag)

    // Навигация к указанному фрагменту
    fun navigateTo(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }
}
