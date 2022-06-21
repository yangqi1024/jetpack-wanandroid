package cn.idesign.architecture.data.source.local


import cn.idesign.architecture.data.UserInfo
import cn.idesign.architecture.data.source.LocalDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class LocalDataSourceImpl internal constructor(
    private val userDao: UserDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : LocalDataSource {
    override suspend fun saveUserInfo(userInfo: UserInfo) = with(ioDispatcher) {
        userDao.insertUserInfo(userInfo)
    }
}