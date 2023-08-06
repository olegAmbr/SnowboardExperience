package di

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.snowboardexperience.R
import com.example.snowboardexperience.databinding.FragmentTechItemDetailsBinding
import com.google.android.material.snackbar.Snackbar

@Suppress("DEPRECATION")
class TechItemDetailsFragment : Fragment() {

    private var _binding: FragmentTechItemDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var techItem: TechItem

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTechItemDetailsBinding.inflate(inflater, container, false)
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
            // Используйте techItem для установки деталей в вашем фрагменте
            setTechItemDetails()
        }
        binding.detailsFabFavorites.setOnClickListener {
            if (!this.techItem.isInFavorites) {
                binding.detailsFabFavorites.setImageResource(R.drawable.round_favorite)
                techItem.isInFavorites = true
            } else {
                binding.detailsFabFavorites.setImageResource(R.drawable.round_favorite_border)
                techItem.isInFavorites = false
            }
        }

        binding.detailsFabShare.setOnClickListener {
            //Создаем интент
            val intent = Intent()
            //Укзываем action с которым он запускается
            intent.action = Intent.ACTION_SEND
            //Кладем данные о нашем фильме
            intent.putExtra(
                Intent.EXTRA_TEXT,
                "Check out this film: ${techItem.title} \n\n ${techItem.description}"
            )
            //УКазываем MIME тип, чтобы система знала, какое приложения предложить
            intent.type = "text/plain"
            //Запускаем наше активити
            startActivity(Intent.createChooser(intent, "Share To:"))
        }
    }

    private fun setTechItemDetails() {
        //Получаем наш фильм из переданного бандла
        this.techItem = arguments?.get("techItem") as TechItem

        //Устанавливаем заголовок
        binding.detailsToolbar.title = this.techItem.title
        //Устанавливаем картинку
        binding.techItemImageDetails.setImageResource(this.techItem.img)
        //Устанавливаем описание
        binding.techItemDescriptionDetails.text = this.techItem.description

        binding.detailsFabFavorites.setImageResource(
            if (this.techItem.isInFavorites) R.drawable.round_favorite
            else R.drawable.round_favorite_border
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_TECH_ITEM = "tech_item"
        private const val ARG_IMG = "img"
        private const val ARG_TITLE = "title"
        private const val ARG_DESCRIPTION = "description"

        fun newInstance(imageResId: Int, title: String, description: String): TechItemDetailsFragment {
            val args = Bundle()
            args.putInt(ARG_IMG, imageResId)
            args.putString(ARG_TITLE, title)
            args.putString(ARG_DESCRIPTION, description)
            val fragment = TechItemDetailsFragment()
            fragment.arguments = args
            return fragment
        }
        // Перегруженная версия для передачи TechItem
        fun newInstance(techItem: TechItem): TechItemDetailsFragment {
            val args = Bundle()
            args.putParcelable(ARG_TECH_ITEM, techItem)
            val fragment = TechItemDetailsFragment()
            fragment.arguments = args
            return fragment
        }
    }
    private fun setupFabFavorites() {
        binding.detailsFabFavorites.setOnClickListener {
            addToFavorites(techItem)
            showSnackBar("Added to Favorites")
        }
    }

    private fun addToFavorites(techItem: TechItem) {
        FavoritesManager.addFavorite(techItem)
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }
}
