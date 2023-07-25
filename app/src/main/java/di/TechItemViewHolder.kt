package di

import androidx.recyclerview.widget.RecyclerView
import com.example.snowboardexperience.databinding.TechItemBinding

class TechItemViewHolder(private val binding: TechItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(techItem: TechDataBase) {
        // Привязка данных к элементам разметки
        binding.techItem = techItem
        binding.executePendingBindings()
    }
}