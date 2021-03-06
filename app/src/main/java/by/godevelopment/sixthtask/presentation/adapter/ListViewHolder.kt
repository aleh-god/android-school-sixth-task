package by.godevelopment.sixthtask.presentation.adapter

import android.R
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import by.godevelopment.sixthtask.commons.NO_CHOICE_STRING_VALUE
import by.godevelopment.sixthtask.databinding.ItemInputListBinding
import by.godevelopment.sixthtask.domain.models.ListItemModel

class ListViewHolder(
    private val binding: ItemInputListBinding,
    private val setResultToList: (Int, String, String) -> Unit
) : RecyclerView.ViewHolder(binding.root), MetaViewHolder {
    override fun bind(item: ListItemModel) {
        val listValues = item.values?.map {
            it.value
        } ?: emptyList()
        binding.apply {
            fieldName.text = item.title
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
                        setResultToList(item.id, item.name, choose)
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        setResultToList(item.id, item.name, NO_CHOICE_STRING_VALUE)
                    }
                }
        }
    }
}