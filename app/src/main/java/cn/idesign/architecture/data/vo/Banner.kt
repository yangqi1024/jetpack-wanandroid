package cn.idesign.architecture.data.vo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Banner(
    @PrimaryKey(autoGenerate = true) val customId: Long,
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "desc") val desc: String,
    @ColumnInfo(name = "image_path") val imagePath: String,
    @ColumnInfo(name = "is_visible") val isVisible: Int,
    @ColumnInfo(name = "order") val order: Int,
    @ColumnInfo(name = "type") val type: Int,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "title") val title: String,
)

