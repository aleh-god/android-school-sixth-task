package by.godevelopment.sixthtask.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.godevelopment.sixthtask.commons.LIST_ROW_TYPE
import by.godevelopment.sixthtask.commons.NUMERIC_ROW_TYPE
import by.godevelopment.sixthtask.commons.TEXT_ROW_TYPE
import by.godevelopment.sixthtask.databinding.ItemInputListBinding
import by.godevelopment.sixthtask.databinding.ItemInputNumericBinding
import by.godevelopment.sixthtask.databinding.ItemInputTextBinding

class ViewHolderFactory(
    private val setResult: (Int, String, String) -> Unit
) {
    fun buildHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            TEXT_ROW_TYPE -> TextViewHolder(
                ItemInputTextBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false),
                setResult
            )
            NUMERIC_ROW_TYPE -> NumericViewHolder(
                ItemInputNumericBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false),
                setResult
            )
            LIST_ROW_TYPE -> ListViewHolder(
                ItemInputListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false),
                setResult
            )
            else -> throw IllegalStateException()
        }
}