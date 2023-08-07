package di

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.snowboardexperience.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        // Доступ к элементам разметки через объект привязки
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button1.setOnClickListener {
            (activity as? MainActivity)?.navigateTo(SkiingTechFragment())
        }

        binding.button2.setOnClickListener {
            (activity as? MainActivity)?.navigateTo(EquipmentFragment())
        }

        binding.button3.setOnClickListener {
            (activity as? MainActivity)?.navigateTo(SkiingConditionFragment())
        }

        /*   binding.button4.setOnClickListener {
               (activity as? MainActivity)?.navigateTo(PlacesFragment())
           } */
    }
}