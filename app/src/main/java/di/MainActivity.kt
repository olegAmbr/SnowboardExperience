package di

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.snowboardexperience.R
import com.example.snowboardexperience.databinding.ActivityMainBinding
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var dependency: Dependency

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Установить Toolbar как ActionBar
        setSupportActionBar(binding.topAppBarMenu)

        // Установить своё собственное название для активити
        supportActionBar?.title = "Snow"

        if (savedInstanceState == null) {
            // При первом создании активности добавляем MainFragment в контейнер
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, HomeFragment())
                .commit()

            // Инициализация Dagger
            val component = DaggerAppComponent.builder().build()
            component.inject(this)

            // Теперь вы можете использовать someDependency в активности
            val message = Dependency()
            Log.d("MainActivity", message.toString())

            supportFragmentManager.beginTransaction()
                .replace(R.id.container, HomeFragment())
                .commit()
        }

        fun changeFragment(fragment: Fragment, tag: String) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, fragment, tag)
                .addToBackStack(null)
                .commit()
        }

        fun checkFragmentExistence(tag: String): Fragment? =
            supportFragmentManager.findFragmentByTag(tag)

        binding.bottomNavigationMenu.setOnNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.home -> {
                    val tag = "home"
                    val fragment = checkFragmentExistence(tag)
                    //В первом параметре, если фрагмент не найден и метод вернул null, то с помощью
                    //элвиса мы вызываем создание нового фрагмента
                    changeFragment(fragment ?: HomeFragment(), tag)
                    true
                }
                R.id.favorites -> {
                    val tag = "favorites"
                    val fragment = checkFragmentExistence(tag)
                    changeFragment(fragment ?: FavoritesFragment(), tag)
                    true
                }

                else -> false
            }
        }

    }

    fun navigateTo(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }
}