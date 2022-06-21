package cn.idesign.architecture.data.source.remote

import cn.idesign.architecture.data.Result
import cn.idesign.architecture.data.source.RemoteDataSource

object RemoteDataSourceImpl: RemoteDataSource {
    override suspend fun login(username: String, password: String): Result<Nothing> {
        TODO("Not yet implemented")
    }
}