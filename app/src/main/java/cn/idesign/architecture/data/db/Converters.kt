package cn.idesign.architecture.data.db

import androidx.room.TypeConverter
import cn.idesign.architecture.data.vo.Tag
import cn.idesign.architecture.utils.GsonManager
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun listToString(tags: List<Tag>): String {
        return GsonManager.getInstance().toJson(tags)
    }

    @TypeConverter
    fun stringToList(json: String): List<Tag> {
        val listType = object : TypeToken<List<Tag>>() {}.type
        return GsonManager.getInstance().fromJson(json, listType)
    }
}