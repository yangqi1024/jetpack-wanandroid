package cn.idesign.architecture.data.source


import cn.idesign.architecture.data.Result
import cn.idesign.architecture.data.UserInfo

interface RemoteDataSource {
    suspend fun login(username: String, password: String): Result<Nothing>
}