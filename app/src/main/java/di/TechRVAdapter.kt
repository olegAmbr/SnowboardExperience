package di

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.snowboardexperience.databinding.TechItemBinding

class TechRVAdapter(private val onItemClick: (TechDataBase) -> Unit) :
    RecyclerView.Adapter<TechRVAdapter.TechItemViewHolder>() {

    private val items = mutableListOf<TechDataBase>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<TechDataBase>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TechItemViewHolder {
        val binding = TechItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TechItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TechItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class TechItemViewHolder(private val binding: TechItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onItemClick(items[adapterPosition])
            }
        }

        fun bind(data: TechDataBase) {
            binding.img.setImageResource(data.img)
            binding.title.text = data.title
        }
    }
}