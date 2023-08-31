package di

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.snowboardexperience.databinding.TechItemBinding

// Адаптер для RecyclerView, отображающий список технологических элементов
class TechRVAdapter(
    private val items: List<TechItem>, // Список технологических элементов для отображения
    private val itemClickListener: TechItemClickListener // Интерфейс слушателя кликов на элементах списка
) : RecyclerView.Adapter<TechRVAdapter.ViewHolder>() {

    // Создание нового ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = TechItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    // Привязка данных технологического элемента к ViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    // Получение общего количества элементов в списке
    override fun getItemCount(): Int = items.size

    // Вложенный класс ViewHolder, содержащий привязки элементов UI
    inner class ViewHolder(private val binding: TechItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        // Привязка данных элемента к View
        fun bind(item: TechItem) {
            binding.techItemImage.setImageResource(item.img) // Установка изображения
            binding.techItemTitle.text = item.title // Установка заголовка
            binding.root.setOnClickListener {
                itemClickListener.onItemClick(item) // Обработка клика на элементе
            }
        }
    }
}
