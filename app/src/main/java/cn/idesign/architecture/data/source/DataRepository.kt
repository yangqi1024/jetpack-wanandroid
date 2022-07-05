package cn.idesign.architecture.data.source

import androidx.paging.PagingData
import cn.idesign.architecture.data.dto.CommonResponse
import cn.idesign.architecture.data.vo.Article
import cn.idesign.architecture.data.vo.Banner
import cn.idesign.architecture.data.vo.UserInfo
import kotlinx.coroutines.flow.Flow

interface DataRepository {
    suspend fun login(username: String, password: String): CommonResponse<Nothing>

    suspend fun saveUserInfo(userInfo: UserInfo)

    fun getRecommendData(): Flow<PagingData<Article>>

    fun getWenDaData(): Flow<PagingData<Article>>

    fun getProjectData(): Flow<PagingData<Article>>

    fun getHomeBanner(): Flow<List<Banner>>
}