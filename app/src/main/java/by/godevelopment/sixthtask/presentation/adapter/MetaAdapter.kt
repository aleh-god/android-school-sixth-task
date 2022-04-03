package by.godevelopment.sixthtask.presentation.adapter
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.godevelopment.sixthtask.domain.models.ListItemModel

class MetaAdapter (
    private val metaList: List<ListItemModel>,
    setResult: (Int, String, String) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val viewHolderFactory: ViewHolderFactory = ViewHolderFactory(setResult)

    override fun getItemViewType(position: Int): Int {
        return metaList[position].itemViewType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return viewHolderFactory.buildHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = metaList[position]
        val metaHolder = holder as MetaViewHolder
        metaHolder.bind(item)
    }

    override fun getItemCount(): Int = metaList.size
}