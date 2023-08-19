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

    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val favorites: MutableList<TechItem> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        sharedPreferences = requireContext().getSharedPreferences("favorites", Context.MODE_PRIVATE)
        setupRecyclerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateFavoritesList()
    }

    private fun setupRecyclerView() {
        val adapter = TechRVAdapter(favorites, object : TechItemClickListener {
            override fun onItemClick(techItem: TechItem) {
                val detailsFragment = TechItemDetailsFragment.newInstance(techItem)
                parentFragmentManager.beginTransaction()
                    .replace(R.id.container, detailsFragment)
                    .addToBackStack(null)
                    .commit()
            }
        })
        binding.rvFavorites.adapter = adapter
        binding.rvFavorites.layoutManager = LinearLayoutManager(requireContext())
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateFavoritesList() {
        favorites.clear()
        favorites.addAll(getFavoriteTechItemsFromSharedPreferences())
        binding.rvFavorites.adapter?.notifyDataSetChanged()
    }

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

      companion object {
        const val TAG = "FavoritesFragment"
    }
}