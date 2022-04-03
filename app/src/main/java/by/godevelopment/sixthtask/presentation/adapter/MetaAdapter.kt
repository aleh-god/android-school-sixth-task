package by.godevelopment.sixthtask.presentation.adapter
import android.R
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import by.godevelopment.sixthtask.commons.LIST_ROW_TYPE
import by.godevelopment.sixthtask.commons.NUMERIC_ROW_TYPE
import by.godevelopment.sixthtask.commons.TAG
import by.godevelopment.sixthtask.commons.TEXT_ROW_TYPE
import by.godevelopment.sixthtask.databinding.ItemInputListBinding
import by.godevelopment.sixthtask.databinding.ItemInputNumericBinding
import by.godevelopment.sixthtask.databinding.ItemInputTextBinding
import by.godevelopment.sixthtask.domain.models.ListItemModel

class MetaAdapter (
    private val metaList: List<ListItemModel>,
    private val setResultToHeader: (Int, String, String?) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return metaList[position].itemViewType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TEXT_ROW_TYPE -> TextViewHolder(
                ItemInputTextBinding.inflate(LayoutInflater.from(parent.context),
                    parent,
                    false
                ))
            NUMERIC_ROW_TYPE -> NumericViewHolder(
                ItemInputNumericBinding.inflate(LayoutInflater.from(parent.context),
                    parent,
                    false
                ))
            LIST_ROW_TYPE -> ListViewHolder(
                ItemInputListBinding.inflate(LayoutInflater.from(parent.context),
                    parent,
                    false
                ))
            else -> throw IllegalStateException()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = metaList[position]
        when (holder) {
            is TextViewHolder -> {
                val binding = holder.binding
                Log.i(TAG, "TextViewHolder bind: $item")
                binding.fieldName.text = item.title
                binding.inputText.apply {
                    var textBuffer: String? = item.result
                    textBuffer?.let {
                        setText(it)
                        setSelection(it.length)
                    }
                    addTextChangedListener { textBuffer = it.toString() }
                    setOnFocusChangeListener { _, hasFocus ->
                        if (!hasFocus) { setResultToHeader(item.id, "TEXT", textBuffer) }
                    }
                }
            }
            is NumericViewHolder -> {
                val binding = holder.binding
                Log.i(TAG, "NumericViewHolder bind: $item")
                binding.fieldName.text = item.title
                binding.inputNumber.apply {
                    var textBuffer: Double? = item.result?.toDoubleOrNull()
                    textBuffer?.let {
                        setText(it.toString())
                    }
                    addTextChangedListener { textBuffer = it.toString().toDoubleOrNull() }
                    setOnFocusChangeListener { _, hasFocus ->
                        if (!hasFocus) { setResultToHeader(item.id, "Numeric", textBuffer.toString()) }
                    }
                }
            }
            is ListViewHolder -> {
                val binding = holder.binding
                Log.i(TAG, "ListViewHolder bind: $item")
                binding.apply {
                    fieldName.text = item.title
                    val listValues = item.values?.map {
                        it.value
                    } ?: emptyList()
                    ArrayAdapter(
                        root.context,
                        R.layout.simple_spinner_item,
                        listValues // arrayOf("one", "two")
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
                                setResultToHeader(item.id, "LIST", choose)
                            }

                            override fun onNothingSelected(p0: AdapterView<*>?) {
                                Log.i(TAG, "ItemViewHolder choose: Не выбрано")
                                setResultToHeader(item.id, "LIST", "Не выбрано")
                            }
                        }
                }
            }
            else -> throw IllegalStateException()
        }
    }

    override fun getItemCount(): Int = metaList.size

    inner class TextViewHolder(
        val binding: ItemInputTextBinding
    ) : RecyclerView.ViewHolder(binding.root)

    inner class NumericViewHolder(
        val binding: ItemInputNumericBinding
    ) : RecyclerView.ViewHolder(binding.root)

    inner class ListViewHolder(
        val binding: ItemInputListBinding
    ) : RecyclerView.ViewHolder(binding.root)
}