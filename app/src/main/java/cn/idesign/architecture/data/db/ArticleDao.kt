package cn.idesign.architecture.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import cn.idesign.architecture.data.vo.Article

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pokemonList: List<Article>)

    @Query("SELECT * FROM Article")
    fun get(): PagingSource<Int, Article>

    @Query("DELETE FROM Article")
    suspend fun clear()

    @Delete
    fun delete(repo: Article)

    @Update
    fun update(repo: Article)
}
