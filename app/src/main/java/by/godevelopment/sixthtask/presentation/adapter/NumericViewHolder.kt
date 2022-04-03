package by.godevelopment.sixthtask.presentation.adapter

import android.util.Log
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import by.godevelopment.sixthtask.commons.TAG
import by.godevelopment.sixthtask.databinding.ItemInputNumericBinding
import by.godevelopment.sixthtask.domain.models.ListItemModel

class NumericViewHolder(
    private val binding: ItemInputNumericBinding,
    private val setResultToHeader: (Int, String, String) -> Unit
) : RecyclerView.ViewHolder(binding.root), MetaViewHolder {
    override fun bind(item: ListItemModel) {
        Log.i(TAG, "NumericViewHolder bind: $item")
        binding.fieldName.text = item.title
        binding.inputNumber.apply {
            var textBuffer: Double? = item.result?.toDoubleOrNull()
            textBuffer?.let {
                setText(it.toString())
            }
            addTextChangedListener { textBuffer = it.toString().toDoubleOrNull() }
            setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) { setResultToHeader(item.id, item.name, textBuffer.toString()) }
            }
        }
    }
}