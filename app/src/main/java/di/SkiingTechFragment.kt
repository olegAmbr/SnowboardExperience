package di

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.snowboardexperience.R
import com.example.snowboardexperience.databinding.FragmentSkiingTechBinding


@Suppress("UNREACHABLE_CODE")
class SkiingTechFragment : Fragment() {
    private lateinit var binding: FragmentSkiingTechBinding
    private lateinit var techRecyclerViewAdapter: TechRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSkiingTechBinding.inflate(inflater, container, false)
        return binding.root
        //находим наш RV
        binding.techRecycler.apply {
            //Инициализируем наш адаптер в конструктор передаем анонимно инициализированный интерфейс,
            //оставим его пока пустым, он нам понадобится во второй части задания
            techRecyclerViewAdapter = TechRecyclerViewAdapter(object : TechRecyclerViewAdapter.OnItemClickListener{
                override fun click(techDataBase: TechDataBase) {
                    //Создаем бандл и кладем туда объект с данными фильма
                    val bundle = Bundle()
                    //Первым параметром указывается ключ, по которому потом будем искать, вторым сам
                    //передаваемый объект
                   // bundle.putParcelable("techDataBase", techDataBase)
                    //Запускаем наше активити
                    val intent = Intent(this@SkiingTechFragment, TechItemDetailsFragment::class.java)
                    //Прикрепляем бандл к интенту
                    intent.putExtras(bundle)
                    //Запускаем активити через интент
                    startActivity(intent)
                }
            })
            //Присваиваем адаптер
            binding.techRecycler.adapter = techRecyclerViewAdapter
            //Присвои layoutmanager
            binding.techRecycler.layoutManager = LinearLayoutManager(this@SkiingTechFragment)
            //Применяем декоратор для отступов
           // val decorator = TopSpacingItemDecoration(8)
         //   binding.techRecycler.addItemDecoration(decorator)
        }
//Кладем нашу БД в RV
        techRecyclerViewAdapter.addItems(techDataBase)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
    val techDataBase = listOf(
        TechDataBase("Фрирайд (Freeride)", R.drawable.skiing, "Фрирайд - это наиболее распространенный стиль катания на сноуборде. Райдеры, занимающиеся фрирайдом, предпочитают кататься вне трасс и наслаждаться естественными условиями горы. Они ищут свободные поверхности, свежий глубокий снег, необжитые участки и темные лесные трассы. Фрирайдеры стремятся кататься с максимальным контролем и безопасностью, выбирая оптимальные линии спуска по склонам горы."),
        TechDataBase("Фристайл (Freestyle)", R.drawable.skiing, "Фристайл - это стиль катания, ориентированный на трюки и фигуры. Райдеры, занимающиеся фристайлом, предпочитают кататься на специально оборудованных снежных парках (snow park), где есть прыжки, рейлы, боксы и другие элементы для выполнения разнообразных трюков. Они выполняют вращения, флипы (перевороты), грабы (захваты) и другие сложные трюки, чтобы проявить свою креативность и мастерство."),
        TechDataBase("Флэт фристайл (Flat Freestyle)", R.drawable.skiing, "Флэт фристайл - это стиль катания на сноуборде, который предполагает выполнение трюков и фигур на плоской поверхности без использования специальных препятствий, таких как прыжки или рейлы. Райдеры, занимающиеся флэт фристайлом, могут выполнять различные трюки, такие как грабы (захваты), вращения, перевороты и другие фигуры, используя плоские участки снега, равнины или небольшие барьеры.\n" +
                "\n" +
                "В отличие от традиционного фристайла, который выполняется в специально оборудованных снежных парках с препятствиями, флэт фристайл предоставляет райдерам больше свободы и творчества, так как они могут выполнять трюки практически в любом месте на склоне."),
        TechDataBase("Джиббинг (Jibbing)", R.drawable.skiing, "Джиббинг - это стиль катания, в котором акцент делается на скольжении и выполнении трюков на препятствиях, таких как боксы, рейлы, трубы и другие элементы в снежных парках или вне трассы. Райдеры, занимающиеся джиббингом, умело маневрируют по препятствиям, гриндят (скользят) по поверхностям и выполняют разнообразные трюки."),
        TechDataBase("Карвинг (Carving)", R.drawable.skiing, "Карвинг - это стиль катания, в котором акцент делается на плавных и контролируемых поворотах. Райдеры, занимающиеся карвингом, используют жесткие сноуборды и специальные техники, чтобы создать глубокие и изящные повороты на склонах. Они активно переносят вес с канта на кант, что позволяет им поддерживать высокую скорость и отличную стабильность."),
        TechDataBase("Бэккантри (Backcountry)", R.drawable.skiing, "Бэккантри - это стиль катания на сноуборде, связанный с катанием вне горнолыжных курортов и трасс. Райдеры, занимающиеся бэккантри, исследуют необжитые районы гор и глухие участки снега, часто с использованием специального снаряжения, такого как лавинные рюкзаки и лопаты. Они наслаждаются природной красотой гор и высокой степенью свободы, но это также требует хорошей подготовки и понимания лавинной безопасности."),
        TechDataBase("Паудер (Powder)", R.drawable.skiing, "Паудер - это стиль катания, ориентированный на катание в глубоком снегу. Райдеры, занимающиеся паудером, ищут участки с мягким и свежим снегом, чтобы насладиться плавным и нежным катанием. Катание по глубокому снегу предоставляет особое удовольствие и уникальные ощущения для райдеров, и это требует некоторого опыта и навыков для успешного спуска.")

    )
}