package di

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.snowboardexperience.R
import com.example.snowboardexperience.databinding.FragmentTechItemDetailsBinding
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import di.FavoritesFragment.Companion.TAG


@Suppress("DEPRECATION")
class TechItemDetailsFragment : Fragment() {

    private var _binding: FragmentTechItemDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var techItem: TechItem
    private lateinit var sharedPreferences: SharedPreferences
    private var isInFavorites: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Инфлейт макета деталей технологического элемента с использованием Data Binding
        _binding = FragmentTechItemDetailsBinding.inflate(inflater, container, false)
        sharedPreferences = requireContext().getSharedPreferences("favorite_tech_items", Context.MODE_PRIVATE)
        // Настройка кнопки "Избранное"
        setupFabFavorites()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Получение данных из аргументов и отображение их
        val img = arguments?.getInt(ARG_IMG) ?: 0
        val description = arguments?.getString(ARG_DESCRIPTION) ?: ""

        binding.techItemImageDetails.setImageResource(img)
        binding.techItemDescriptionDetails.text = description

        // Получение данных о технологическом элементе из аргументов
        arguments?.let {
            techItem = it.getParcelable(ARG_TECH_ITEM) ?: TechItem("", 0, "")
            isInFavorites = it.getBoolean(ARG_IS_IN_FAVORITES, false)
            // Отображение деталей технологического элемента
            setTechItemDetails()
        }

        // Обработчик кнопки "Поделиться"
        binding.detailsFabShare.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(
                Intent.EXTRA_TEXT,
                "Check out this item: ${techItem.title} \n\n ${techItem.description}"
            )
            intent.type = "text/plain"
            startActivity(Intent.createChooser(intent, "Share To:"))
        }
    }

    // Установка деталей технологического элемента
    private fun setTechItemDetails() {
        binding.detailsToolbar.title = techItem.title
        binding.techItemImageDetails.setImageResource(techItem.img)
        binding.techItemDescriptionDetails.text = techItem.description
        binding.detailsFabFavorites.setImageResource(
            if (techItem.isInFavorites) R.drawable.round_favorite
            else R.drawable.round_favorite_border
        )
    }

    // Изменение состояния "Избранное" и обновление отображения
    private fun toggleFavoriteState() {
        techItem.isInFavorites = !techItem.isInFavorites
        Log.d(TAG, "Toggling favorite state to: ${techItem.isInFavorites}")
        updateFavoriteStateInSharedPreferences()
        saveFavoriteTechItemsToSharedPreferences()
        val favoritesFragment = parentFragmentManager.findFragmentByTag(TAG) as? FavoritesFragment
        favoritesFragment?.updateFavoritesList()
        binding.detailsFabFavorites.setImageResource(
            if (techItem.isInFavorites) R.drawable.round_favorite
            else R.drawable.round_favorite_border
        )
    }

    // Обновление состояния "Избранное" в SharedPreferences
    private fun updateFavoriteStateInSharedPreferences() {
        Log.d("TechItemDetailsFragment", "Updating favorite state in SharedPreferences")
        val favoriteTechItems = getFavoriteTechItemsFromSharedPreferences()
        val gson = Gson()
        val favoriteTechItemsJson = gson.toJson(favoriteTechItems)
        sharedPreferences.edit().putString("favorite_tech_items", favoriteTechItemsJson).apply()
    }

    // Получение списка "Избранное" из SharedPreferences
    private fun getFavoriteTechItemsFromSharedPreferences(): MutableList<TechItem> {
        val savedTechItemsJson = sharedPreferences.getString("favorite_tech_items", null)
        return if (!savedTechItemsJson.isNullOrBlank()) {
            try {
                val typeToken = object : TypeToken<MutableList<TechItem>>() {}.type
                Gson().fromJson(savedTechItemsJson, typeToken)
            } catch (e: Exception) {
                Log.e("FavoritesFragment", "Error deserializing tech items", e)
                mutableListOf()
            }
        } else {
            mutableListOf()
        }
    }


    // Отображение всплывающего сообщения
    private fun showSnackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    companion object {
        private const val ARG_TECH_ITEM = "tech_item"
        private const val ARG_IMG = "img"
        private const val ARG_DESCRIPTION = "description"
        private const val ARG_IS_IN_FAVORITES = "favorite_tech_items"

        // Создание нового экземпляра фрагмента с передачей аргументов
        fun newInstance(techItem: TechItem, isInFavorites: Boolean): TechItemDetailsFragment {
            val fragment = TechItemDetailsFragment()
            val args = Bundle()
            args.putParcelable(ARG_TECH_ITEM, techItem)
            args.putBoolean(ARG_IS_IN_FAVORITES, isInFavorites)
            fragment.arguments = args
            return fragment
        }
    }

    // Настройка кнопки "Избранное"
    private fun setupFabFavorites() {
        binding.detailsFabFavorites.setOnClickListener {
            val favoritesFragment =
                parentFragmentManager.findFragmentByTag(TAG) as? FavoritesFragment
            toggleFavoriteState() // Переключение состояния избранного
            updateFavoriteStateInSharedPreferences() // Сохранение в Shared Preferences
            favoritesFragment?.updateFavoritesList() // Обновление списка избранных
            showSnackBar(if (techItem.isInFavorites) "Added to Favorites" else "Removed from Favorites")
        }
    }

    // Сохранение списка "Избранное" в SharedPreferences при остановке фрагмента
    private fun saveFavoriteTechItemsToSharedPreferences() {
        Log.d("TechItemDetailsFragment", "Saving favorite tech items to SharedPreferences")
        val favoriteTechItems = getFavoriteTechItemsFromSharedPreferences()
        val gson = Gson()
        val favoriteTechItemsJson = gson.toJson(favoriteTechItems)
        sharedPreferences.edit().putString("favorite_tech_items", favoriteTechItemsJson).apply()
    }

    // Сохранение списка "Избранное" в SharedPreferences при остановке фрагмента
    override fun onStop() {
        super.onStop()
    }

    // Очистка привязки View Binding при уничтожении фрагмента
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
