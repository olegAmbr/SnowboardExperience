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

        if (savedInstanceState == null) {
            // При первом создании активности добавляем MainFragment в контейнер
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, HomeFragment())
                .commit()

            // Инициализация Dagger
            val component = AppComponent.builder().build()
            component.inject(this)

            // Теперь вы можете использовать someDependency в активности
            val message = Dependency()
            Log.d("MainActivity", message.toString())

            supportFragmentManager.beginTransaction()
                .replace(R.id.container, HomeFragment())
                .commit()
        }


    }
    fun navigateTo(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }
}