package cn.idesign.architecture.data.source

import cn.idesign.architecture.data.Result
import cn.idesign.architecture.data.UserInfo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class DefaultDataRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : DataRepository {
    override suspend fun login(username: String, password: String): Result<Nothing> {
        return remoteDataSource.login(username, password)
    }

    override suspend fun saveUserInfo(userInfo: UserInfo) {
        localDataSource.saveUserInfo(userInfo)
    }

}
