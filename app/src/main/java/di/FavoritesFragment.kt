package di

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.snowboardexperience.R
import com.example.snowboardexperience.databinding.FragmentFavoritesBinding

class FavoritesFragment : Fragment() {
    private lateinit var binding: FragmentFavoritesBinding
    private val favorites: MutableList<TechItem> = mutableListOf() // Здесь храните список избранных элементов

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        setupRecyclerView()
        return binding.root
    }

    private fun setupRecyclerView() {
        val adapter = TechRVAdapter(favorites, object : TechItemClickListener {
            override fun onItemClick(techItem: TechItem) {
                // Откройте детали избранного элемента при нажатии на него
                showTechItemDetails(techItem)
            }
        })
        binding.rvFavorites.adapter = adapter
    }

    private fun showTechItemDetails(techItem: TechItem) {
        val detailsFragment = TechItemDetailsFragment.newInstance(techItem)
        parentFragmentManager.beginTransaction()
            .replace(R.id.container, detailsFragment)
            .addToBackStack(null)
            .commit()
    }

}