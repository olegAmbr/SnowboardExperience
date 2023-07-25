package di

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.snowboardexperience.databinding.TechItemBinding

class TechViewHolder (private val itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val binding = TechItemBinding.bind(itemView)

    //В этом методе кладем данные из Film в наши View
    fun bind(techDataBase: TechDataBase) {
        //Устанавливаем заголовок
        binding.title.text = techDataBase.title
        //Устанавливаем постер
        binding.img.setImageResource(techDataBase.img)
        //Устанавливаем описание
        binding.description.text = techDataBase.description
    }
}