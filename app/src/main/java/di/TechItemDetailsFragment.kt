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
import androidx.lifecycle.lifecycleScope
import com.example.snowboardexperience.R
import com.example.snowboardexperience.databinding.FragmentTechItemDetailsBinding
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
class TechItemDetailsFragment : Fragment() {

    private var _binding: FragmentTechItemDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var techItem: TechItem
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTechItemDetailsBinding.inflate(inflater, container, false)
        sharedPreferences = requireContext().getSharedPreferences("favorites", Context.MODE_PRIVATE)
        setupFabFavorites()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val img = arguments?.getInt(ARG_IMG) ?: 0
        val description = arguments?.getString(ARG_DESCRIPTION) ?: ""

        binding.techItemImageDetails.setImageResource(img)
        binding.techItemDescriptionDetails.text = description

        arguments?.getParcelable<TechItem>(ARG_TECH_ITEM)?.let {
            techItem = it
            setTechItemDetails()
        }

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

    private fun setTechItemDetails() {
        binding.detailsToolbar.title = techItem.title
        binding.techItemImageDetails.setImageResource(techItem.img)
        binding.techItemDescriptionDetails.text = techItem.description

        binding.detailsFabFavorites.setImageResource(
            if (techItem.isInFavorites) R.drawable.round_favorite
            else R.drawable.round_favorite_border
        )
    }

    private fun toggleFavoriteState() {
        techItem.isInFavorites = !techItem.isInFavorites
        val favoritesFragment = parentFragmentManager.findFragmentByTag(FavoritesFragment.TAG) as? FavoritesFragment
        favoritesFragment?.updateFavoritesList()
        binding.detailsFabFavorites.setImageResource(
            if (techItem.isInFavorites) R.drawable.round_favorite
            else R.drawable.round_favorite_border
        )
        saveFavoriteTechItemsToSharedPreferences()
        updateFavoriteStateInSharedPreferences()
    }

    private fun updateFavoriteStateInSharedPreferences() {
        val favoriteTechItems = getFavoriteTechItemsFromSharedPreferences()
        val gson = Gson()
        val favoriteTechItemsJson = gson.toJson(favoriteTechItems)
        sharedPreferences.edit().putString("favorite_tech_items", favoriteTechItemsJson).apply()
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


    private fun showSnackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    companion object {
        private const val ARG_TECH_ITEM = "tech_item"
        private const val ARG_IMG = "img"
        private const val ARG_DESCRIPTION = "description"

        fun newInstance(techItem: TechItem): TechItemDetailsFragment {
            val fragment = TechItemDetailsFragment()
            val args = Bundle()
            args.putParcelable(ARG_TECH_ITEM, techItem)
            fragment.arguments = args
            return fragment
        }
    }

    private fun setupFabFavorites() {
        binding.detailsFabFavorites.setOnClickListener {
            toggleFavoriteState()
            val favoritesFragment =
                parentFragmentManager.findFragmentByTag(FavoritesFragment.TAG) as? FavoritesFragment
            favoritesFragment?.updateFavoritesList()
            showSnackBar(if (techItem.isInFavorites) "Added to Favorites" else "Removed from Favorites")
        }
    }

    private fun saveFavoriteTechItemsToSharedPreferences() {
        val favoriteTechItems = getFavoriteTechItemsFromSharedPreferences()
        val gson = Gson()
        val favoriteTechItemsJson = gson.toJson(favoriteTechItems)
        sharedPreferences.edit().putString("favorite_tech_items", favoriteTechItemsJson).apply()
    }

    override fun onStop() {
        super.onStop()
        saveFavoriteTechItemsToSharedPreferences()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
