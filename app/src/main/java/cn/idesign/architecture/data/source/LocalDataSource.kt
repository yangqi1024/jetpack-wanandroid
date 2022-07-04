package cn.idesign.architecture.data.source


import cn.idesign.architecture.data.vo.UserInfo

interface LocalDataSource {
    suspend fun saveUserInfo(userInfo: UserInfo)
}