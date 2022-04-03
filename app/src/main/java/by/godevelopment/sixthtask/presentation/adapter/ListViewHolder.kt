package by.godevelopment.sixthtask.presentation.adapter

import android.R
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import by.godevelopment.sixthtask.commons.NO_CHOICE_STRING_VALUE
import by.godevelopment.sixthtask.commons.TAG
import by.godevelopment.sixthtask.databinding.ItemInputListBinding
import by.godevelopment.sixthtask.domain.models.ListItemModel

class ListViewHolder(
    private val binding: ItemInputListBinding,
    private val setResultToHeader: (Int, String, String) -> Unit
) : RecyclerView.ViewHolder(binding.root), MetaViewHolder {
    override fun bind(item: ListItemModel) {
        Log.i(TAG, "ListViewHolder bind: $item")
        binding.apply {
            fieldName.text = item.title
            val listValues = item.values?.map {
                it.value
            } ?: emptyList()
            ArrayAdapter(
                root.context,
                R.layout.simple_spinner_item,
                listValues
            ).also { adapter ->
                adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
                inputValue.adapter = adapter
                if (item.result != null) {
                    val spinnerPos = adapter.getPosition(item.result)
                    inputValue.setSelection(spinnerPos)
                }
            }
            inputValue.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        p0: AdapterView<*>?,
                        p1: View?,
                        selectedItemPosition: Int,
                        p3: Long
                    ) {
                        val choose = listValues[selectedItemPosition]
                        Log.i(TAG, "ItemViewHolder choose: ${choose}")
                        setResultToHeader(item.id, item.name, choose)
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        Log.i(TAG, "ItemViewHolder choose: Не выбрано")
                        setResultToHeader(item.id, item.name, NO_CHOICE_STRING_VALUE)
                    }
                }
        }
    }
}