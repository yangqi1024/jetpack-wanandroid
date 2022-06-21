package cn.idesign.architecture.data.source


import cn.idesign.architecture.data.Result
import cn.idesign.architecture.data.UserInfo

interface LocalDataSource {
    suspend fun saveUserInfo(userInfo: UserInfo)
}