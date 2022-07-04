package cn.idesign.architecture.data.source


import cn.idesign.architecture.data.dto.CommonResponse
import cn.idesign.architecture.data.vo.Banner
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
    suspend fun login(username: String, password: String): CommonResponse<Nothing>

    fun getHomeBanner(): Flow<List<Banner>>
}