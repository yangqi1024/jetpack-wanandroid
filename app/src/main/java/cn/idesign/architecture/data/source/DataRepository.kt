package cn.idesign.architecture.data.source

import cn.idesign.architecture.data.UserInfo
import cn.idesign.architecture.data.Result

interface DataRepository {
    suspend fun login(username: String, password: String):Result<Nothing>

    suspend fun saveUserInfo(userInfo: UserInfo)
}