package di

<<<<<<< HEAD
import android.annotation.SuppressLint
=======
>>>>>>> origin/sprint_8
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.snowboardexperience.R
import com.example.snowboardexperience.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
<<<<<<< HEAD
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var switchDarkTheme: Switch
    @SuppressLint("UseSwitchCompatOrMaterialCode")
=======
    private lateinit var switchDarkTheme: Switch
>>>>>>> origin/sprint_8
    private lateinit var switchSystemTheme: Switch

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
<<<<<<< HEAD
    ): View {
=======
    ): View? {
>>>>>>> origin/sprint_8
        val binding: FragmentSettingsBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_settings, container, false
        )

        switchDarkTheme = binding.switchDarkTheme
        switchSystemTheme = binding.switchSystemTheme

        val sharedPreferences = requireContext().getSharedPreferences("settings", Context.MODE_PRIVATE)

        switchDarkTheme.isChecked = sharedPreferences.getBoolean("darkThemeEnabled", false)
        switchSystemTheme.isChecked = sharedPreferences.getBoolean("systemThemeEnabled", false)

        switchDarkTheme.setOnCheckedChangeListener { _, isChecked ->
            with(sharedPreferences.edit()) {
                putBoolean("darkThemeEnabled", isChecked)
                apply()
            }
            // Примените настройки темы
            // (вызов метода или обновление ресурсов темы)
        }

        switchSystemTheme.setOnCheckedChangeListener { _, isChecked ->
            with(sharedPreferences.edit()) {
                putBoolean("systemThemeEnabled", isChecked)
                apply()
            }
            // Примените настройки темы
            // (вызов метода или обновление ресурсов темы)
        }

        return binding.root
    }
}