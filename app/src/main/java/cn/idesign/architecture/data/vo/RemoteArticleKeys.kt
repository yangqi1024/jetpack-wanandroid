package cn.idesign.architecture.data.vo

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "remote_article_keys")
data class RemoteArticleKeys(
    @PrimaryKey
    val articleId: Long,
    val prevKey: Int?,
    val nextKey: Int?
)