package by.godevelopment.sixthtask.presentation.adapter

import android.util.Log
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import by.godevelopment.sixthtask.commons.TAG
import by.godevelopment.sixthtask.databinding.ItemInputNumericBinding
import by.godevelopment.sixthtask.domain.models.ListItemModel

class NumericViewHolder(
    private val binding: ItemInputNumericBinding,
    private val setResultToList: (Int, String, String?) -> Unit
) : RecyclerView.ViewHolder(binding.root), MetaViewHolder {
    override fun bind(item: ListItemModel) {
        Log.i(TAG, "NumericViewHolder bind: $item")
        binding.fieldName.text = item.title
        binding.inputNumber.apply {
            var textBuffer: String? = item.result
            setText(textBuffer)
            addTextChangedListener {
                Log.i(TAG, "NumericViewHolder: addTextChangedListener $it")
                textBuffer = it.toString()
            }
            setOnFocusChangeListener { _, hasFocus ->
                Log.i(TAG, "NumericViewHolder: setOnFocusChangeListener $hasFocus")
                if (!hasFocus) {
                    Log.i(TAG, "NumericViewHolder: setResultToList ${item.name} to $textBuffer")
                    setResultToList(item.id, item.name, textBuffer)
                }
            }
        }
    }
}