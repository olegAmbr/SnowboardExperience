package di

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.snowboardexperience.databinding.TechItemBinding



class TechRVAdapter(private val items: List<TechItem>, param: TechItemClickListener) : RecyclerView.Adapter<TechRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = TechItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(private val binding: TechItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TechItem) {
            binding.techItemImage.setImageResource(item.img)
            binding.techItemTitle.text = item.title
    }
}
}
