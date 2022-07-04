package cn.idesign.architecture.data.vo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "UserInfo")
data class UserInfo @JvmOverloads constructor(
    @ColumnInfo(name = "username") var userName: String = "",
    @ColumnInfo(name = "phone") var phone: String = "",
    @PrimaryKey @ColumnInfo(name = "id") var id: String = UUID.randomUUID().toString()
)