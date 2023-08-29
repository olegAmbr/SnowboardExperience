package di

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.snowboardexperience.R
import com.example.snowboardexperience.databinding.FragmentFavoritesBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class FavoritesFragment : Fragment() {

    // Переменная для View Binding
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    // Хранилище для избранных технологических элементов
    private lateinit var sharedPreferences: SharedPreferences

    // Список для хранения избранных элементов
    private val favorites: MutableList<TechItem> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Создание макета фрагмента с использованием View Binding
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)

        // Инициализация Shared Preferences
        sharedPreferences = requireContext().getSharedPreferences("favorite_tech_items", Context.MODE_PRIVATE)

        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Обновление RecyclerView загруженными избранными элементами
        updateFavoritesList()

        binding.rvFavorites.adapter?.notifyDataSetChanged()

        // Настройка RecyclerView
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        // Получение списка избранных элементов из Shared Preferences
        var favorites: MutableList<TechItem> = getFavoriteTechItemsFromSharedPreferences()
        // Настройка RecyclerView с списком избранных технологических элементов
        val adapter = TechRVAdapter(favorites, object : TechItemClickListener {
            override fun onItemClick(techItem: TechItem) {
                // Обработка нажатия на элемент для отображения деталей во втором фрагменте
                val detailsFragment = TechItemDetailsFragment.newInstance(techItem, !techItem.isInFavorites)
                parentFragmentManager.beginTransaction()
                    .add(R.id.container, detailsFragment)
                    .addToBackStack(null)
                    .commit()
            }
        })
        // Подключение адаптера и менеджера компоновки к RecyclerView
        binding.rvFavorites.adapter = adapter
        binding.rvFavorites.layoutManager = LinearLayoutManager(requireContext())
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateFavoritesList() {
        Log.d("FavoritesFragment", "Updating favorites list")
        // Очистка текущего списка избранных и заполнение его данными из Shared Preferences
        favorites.clear()
        favorites.addAll(getFavoriteTechItemsFromSharedPreferences())
        // Уведомление адаптера о изменении набора данных
        binding.rvFavorites.adapter?.notifyDataSetChanged()
    }

    private fun getFavoriteTechItemsFromSharedPreferences(): MutableList<TechItem> {
        // Получение сохраненных технологических элементов из Shared Preferences
        val savedTechItemsJson = sharedPreferences.getString("favorite_tech_items", null)
        return if (!savedTechItemsJson.isNullOrBlank()) {
            try {
                // Десериализация JSON с использованием Gson
                val typeToken = object : TypeToken<MutableList<TechItem>>() {}.type
                Gson().fromJson(savedTechItemsJson, typeToken)
            } catch (e: Exception) {
                // Обработка ошибок десериализации
                Log.e("FavoritesFragment", "Ошибка десериализации элементов", e)
                mutableListOf()
            }
        } else {
            mutableListOf()
        }
    }

    override fun onResume() {
        super.onResume()
        // Обновление списка избранных при возобновлении фрагмента
        updateFavoritesList()
    }

    companion object {
        const val TAG = "FavoritesFragment"
    }
}
