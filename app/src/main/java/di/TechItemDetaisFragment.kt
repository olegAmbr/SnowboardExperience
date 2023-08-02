package di

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.snowboardexperience.databinding.FragmentTechItemDetaisBinding

class TechItemDetailsFragment : Fragment() {

    private var _binding: FragmentTechItemDetaisBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTechItemDetaisBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val img = it.getInt(IMG_KEY, 0)
            val title = it.getString(TITLE_KEY)
            val description = it.getString(DESCRIPTION_KEY)

            binding.techItemImageDetails.setImageResource(img)
            binding.techItemTitleDetails.text = title
            binding.techItemDescriptionDetails.text = description
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val IMG_KEY = "img"
        private const val TITLE_KEY = "title"
        private const val DESCRIPTION_KEY = "description"

        fun newInstance(img: Int, title: String, description: String): TechItemDetailsFragment {
            val fragment = TechItemDetailsFragment()
            val args = Bundle()
            args.putInt(IMG_KEY, img)
            args.putString(TITLE_KEY, title)
            args.putString(DESCRIPTION_KEY, description)
            fragment.arguments = args
            return fragment
        }
    }
}