package cn.idesign.architecture.data.source.remote

import cn.chinaunicom.drone.data.source.api.ApiService
import cn.idesign.architecture.data.dto.CommonResponse
import cn.idesign.architecture.data.source.RemoteDataSource
import cn.idesign.architecture.data.vo.Banner
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.*

class RemoteDataSourceImpl(
    private val service: ApiService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : RemoteDataSource {
    override suspend fun login(username: String, password: String): CommonResponse<Nothing> {
        TODO("Not yet implemented")
    }

    override fun getHomeBanner(): Flow<List<Banner>> {
        return flow {
            println("RemoteDataSourceImpl getHomeBanner,Thread:${Thread.currentThread().name}")
            val homeBanner = service.getHomeBanner()
            if (homeBanner.success()) {
                emit(homeBanner.data)
            } else {
                emit(Collections.emptyList())
            }
        }.flowOn(ioDispatcher).catch {
            emit(Collections.emptyList())
        }
    }
}