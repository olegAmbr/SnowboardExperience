package di

import android.annotation.SuppressLint
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
    // Переключатели для настроек темы
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var switchDarkTheme: Switch
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var switchSystemTheme: Switch

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Инфлейт макета фрагмента с использованием Data Binding
        val binding: FragmentSettingsBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_settings, container, false
        )

        // Привязка переключателей к разметке
        switchDarkTheme = binding.switchDarkTheme
        switchSystemTheme = binding.switchSystemTheme

        // Инициализация Shared Preferences для хранения настроек
        val sharedPreferences = requireContext().getSharedPreferences("settings", Context.MODE_PRIVATE)

        // Установка состояния переключателей на основе сохраненных настроек
        switchDarkTheme.isChecked = sharedPreferences.getBoolean("darkThemeEnabled", false)
        switchSystemTheme.isChecked = sharedPreferences.getBoolean("systemThemeEnabled", false)

        // Обработчик для переключателя "Темная тема"
        switchDarkTheme.setOnCheckedChangeListener { _, isChecked ->
            with(sharedPreferences.edit()) {
                putBoolean("darkThemeEnabled", isChecked)
                apply()
            }
            // Применение настроек темы (например, обновление ресурсов темы)
        }

        // Обработчик для переключателя "Системная тема"
        switchSystemTheme.setOnCheckedChangeListener { _, isChecked ->
            with(sharedPreferences.edit()) {
                putBoolean("systemThemeEnabled", isChecked)
                apply()
            }
            // Применение настроек темы (например, обновление ресурсов темы)
        }

        return binding.root
    }
}
