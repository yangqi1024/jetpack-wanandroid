package cn.idesign.architecture.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cn.idesign.architecture.data.vo.RemoteArticleKeys

@Dao
interface RemoteArticleKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteArticleKeys>)

    @Query("SELECT * FROM remote_article_keys WHERE articleId = :articleId")
    suspend fun remoteKeysArticleId(articleId: Long): RemoteArticleKeys?

    @Query("DELETE FROM remote_article_keys")
    suspend fun clearRemoteKeys()
}
