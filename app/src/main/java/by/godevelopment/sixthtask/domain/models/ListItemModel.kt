package by.godevelopment.sixthtask.domain.models

data class ListItemModel(
    var id: Int = 0,
    val title: String,
    val name: String,
    val type: String,
    val values: Map<String, String>? = null,
    var result: String? = null,
    val itemViewType: Int,
    var haseFocus: Boolean = false
)