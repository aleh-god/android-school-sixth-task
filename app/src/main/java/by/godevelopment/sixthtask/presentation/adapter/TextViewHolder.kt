package by.godevelopment.sixthtask.presentation.adapter

import android.util.Log
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import by.godevelopment.sixthtask.commons.TAG
import by.godevelopment.sixthtask.databinding.ItemInputTextBinding
import by.godevelopment.sixthtask.domain.models.ListItemModel
import kotlin.math.log

class TextViewHolder(
    private val binding: ItemInputTextBinding,
    private val setResultToList: (Int, String, String?) -> Unit
) : RecyclerView.ViewHolder(binding.root), MetaViewHolder {
    override fun bind(item: ListItemModel) {
        Log.i(TAG, "TextViewHolder: bind $item")
        binding.fieldName.text = item.title
        binding.inputText.apply {
            var textBuffer: String? = item.result
            textBuffer?.let {
                setText(it)
                setSelection(it.length)
            }
            addTextChangedListener {
                Log.i(TAG, "TextViewHolder: addTextChangedListener $it")
                textBuffer = it.toString()
            }
            setOnFocusChangeListener { _, hasFocus ->
                Log.i(TAG, "TextViewHolder: setOnFocusChangeListener $hasFocus")
                if (!hasFocus) { setResultToList(item.id, item.name, textBuffer) }
            }
        }
    }
}