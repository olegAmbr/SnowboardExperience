package di

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.snowboardexperience.R
import com.example.snowboardexperience.databinding.FragmentFavoritesBinding
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoritesFragment : Fragment() {
    private lateinit var binding: FragmentFavoritesBinding
    private val favorites: MutableList<TechItem> = mutableListOf()
    @Inject
    lateinit var techItemDao: TechItemDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        setupRecyclerView()
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        if (::techItemDao.isInitialized) {
            updateFavoritesList()
        } // Загружаем избранные элементы при создании фрагмента
    }


    private fun setupRecyclerView() {
        val adapter = TechRVAdapter(SkiingTechFragment().items, object : TechItemClickListener {
            override fun onItemClick(techItem: TechItem) {
                val detailsFragment = TechItemDetailsFragment.newInstance(techItem)
                parentFragmentManager.beginTransaction()
                    .replace(R.id.container, detailsFragment)
                    .addToBackStack(null)
                    .commit()
            }
        })
        binding.rvFavorites.adapter = adapter
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateFavoritesList() {
        lifecycleScope.launch {
            val techItems = techItemDao.getAllTechItems()
            favorites.clear()
            favorites.addAll(techItems.map { techItemEntity ->
                TechItem(
                    title = techItemEntity.title,
                    img = techItemEntity.img,
                    description = techItemEntity.description
                )
            })
            binding.rvFavorites.adapter?.notifyDataSetChanged()
        }
    }

    companion object {
        const val TAG = "FavoritesFragment"
    }
}