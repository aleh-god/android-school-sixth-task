package by.godevelopment.sixthtask.presentation.adapter

import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import by.godevelopment.sixthtask.databinding.ItemInputNumericBinding
import by.godevelopment.sixthtask.domain.models.ListItemModel

class NumericViewHolder(
    private val binding: ItemInputNumericBinding,
    private val setResultToList: (Int, String, String?) -> Unit
) : RecyclerView.ViewHolder(binding.root), MetaViewHolder {
    override fun bind(item: ListItemModel) {
        binding.fieldName.text = item.title
        binding.inputNumber.apply {
            var textBuffer: String? = item.result
            textBuffer?.let {
                setText(it)
                setSelection(it.length)
            }
            addTextChangedListener {
                setResultToList(item.id, item.name, it.toString())
            }
        }
    }
}
