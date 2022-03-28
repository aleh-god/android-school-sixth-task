package by.godevelopment.sixthtask.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import by.godevelopment.sixthtask.databinding.ItemRvBinding

class MetaAdapter() : RecyclerView.Adapter<MetaAdapter.ItemViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
    private val differ = AsyncListDiffer(this, diffCallback)
    var imagesList: List<String>
        get() = differ.currentList
        set(value) { differ.submitList(value) }

    inner class ItemViewHolder(val binding: ItemRvBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MetaAdapter.ItemViewHolder {
        return ItemViewHolder(ItemRvBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: MetaAdapter.ItemViewHolder, position: Int) {
        val item = imagesList[position]
        holder.binding.apply {
            fieldName.text = item
        }
    }

    override fun getItemCount(): Int = imagesList.size
}