package cn.idesign.architecture.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import cn.idesign.architecture.data.vo.Article
import cn.idesign.architecture.data.vo.Banner
import cn.idesign.architecture.data.vo.RemoteArticleKeys
import cn.idesign.architecture.data.vo.UserInfo

@Database(
    entities = [UserInfo::class, Article::class, RemoteArticleKeys::class, Banner::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun articleDao(): ArticleDao
    abstract fun remoteArticleKeysDao(): RemoteArticleKeysDao


    companion object {
        const val DATABASE_NAME = "app-db"

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .build()
        }
    }
}