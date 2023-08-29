package di

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.snowboardexperience.R
import com.example.snowboardexperience.databinding.FragmentSkiingTechBinding


class SkiingTechFragment : Fragment() {

    private var _binding: FragmentSkiingTechBinding? = null
    private val binding get() = _binding!!

    private val items: List<TechItem> = listOf(
        // Список избранных технологических элементов
        TechItem(
            "Фрирайд (Freeride)",
            R.drawable.freeride,
            "Фрирайд - это наиболее распространенный стиль катания на сноуборде. Райдеры, занимающиеся фрирайдом, предпочитают кататься вне трасс и наслаждаться естественными условиями горы. Они ищут свободные поверхности, свежий глубокий снег, необжитые участки и темные лесные трассы. Фрирайдеры стремятся кататься с максимальным контролем и безопасностью, выбирая оптимальные линии спуска по склонам горы."
        ),
        TechItem(
            "Фристайл (Freestyle)",
            R.drawable.freestyle,
            "Фристайл - это стиль катания, ориентированный на трюки и фигуры. Райдеры, занимающиеся фристайлом, предпочитают кататься на специально оборудованных снежных парках (snow park), где есть прыжки, рейлы, боксы и другие элементы для выполнения разнообразных трюков. Они выполняют вращения, флипы (перевороты), грабы (захваты) и другие сложные трюки, чтобы проявить свою креативность и мастерство."
        ),
        TechItem(
            "Флэт фристайл (Flat Freestyle)",
            R.drawable.flat_freestyle,
            "Флэт фристайл - это стиль катания на сноуборде, который предполагает выполнение трюков и фигур на плоской поверхности без использования специальных препятствий, таких как трамплины или рейлы. Райдеры, занимающиеся флэт фристайлом, могут выполнять различные трюки, такие как грабы (захваты), вращения, перевороты и другие фигуры, используя плоские участки снега, равнины или небольшие барьеры.\n" +
                    "\n" +
                    "В отличие от традиционного фристайла, который выполняется в специально оборудованных снежных парках с препятствиями, флэт фристайл предоставляет райдерам больше свободы и творчества, так как они могут выполнять трюки практически в любом месте на склоне."
        ),
        TechItem(
            "Джиббинг (Jibbing)",
            R.drawable.jibbing,
            "Джиббинг - это стиль катания, в котором акцент делается на скольжении и выполнении трюков на препятствиях, таких как боксы, рейлы, трубы и другие элементы в снежных парках или вне трассы. Райдеры, занимающиеся джиббингом, умело маневрируют по препятствиям, гриндят (скользят) по поверхностям и выполняют разнообразные трюки."
        ),
        TechItem(
            "Карвинг (Carving)",
            R.drawable.carving,
            "Карвинг - это стиль катания, в котором акцент делается на плавных и контролируемых поворотах. Райдеры, занимающиеся карвингом, используют жесткие сноуборды и специальные техники, чтобы создать глубокие и изящные повороты на склонах. Они активно переносят вес с канта на кант, что позволяет им поддерживать высокую скорость и отличную стабильность."
        ),
        TechItem(
            "Бэккантри (Backcountry)",
            R.drawable.backcountry,
            "Бэккантри - это стиль катания на сноуборде, связанный с катанием вне горнолыжных курортов и трасс. Райдеры, занимающиеся бэккантри, исследуют необжитые районы гор и глухие участки снега, часто с использованием специального снаряжения, такого как лавинные рюкзаки и лопаты. Они наслаждаются природной красотой гор и высокой степенью свободы, но это также требует хорошей подготовки и понимания лавинной безопасности."
        ),
        TechItem(
            "Паудер (Powder)",
            R.drawable.powder,
            "Паудер - это стиль катания, ориентированный на катание в глубоком снегу. Райдеры, занимающиеся паудером, ищут участки с мягким и свежим снегом, чтобы насладиться плавным и нежным катанием. Катание по глубокому снегу предоставляет особое удовольствие и уникальные ощущения для райдеров, и это требует некоторого опыта и навыков для успешного спуска."
        )

    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Инфлейт макета фрагмента с использованием Data Binding
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_skiing_tech, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Создание адаптера и установка обработчика нажатий
        val adapter = TechRVAdapter(items, object : TechItemClickListener {
            override fun onItemClick(techItem: TechItem) {
                val detailsFragment = TechItemDetailsFragment.newInstance(
                    techItem,
                    techItem.isInFavorites
                )
                parentFragmentManager.beginTransaction()
                    .replace(R.id.container, detailsFragment)
                    .addToBackStack(null)
                    .commit()
            }
        })

        // Настройка RecyclerView
        binding.techRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            this.adapter = adapter
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}