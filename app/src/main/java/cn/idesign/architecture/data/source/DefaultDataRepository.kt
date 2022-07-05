package cn.idesign.architecture.data.source

import androidx.paging.PagingData
import cn.idesign.architecture.data.dto.CommonResponse
import cn.idesign.architecture.data.vo.Article
import cn.idesign.architecture.data.vo.Banner
import cn.idesign.architecture.data.vo.UserInfo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class DefaultDataRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val pageDataSource: PageDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : DataRepository {
    override suspend fun login(username: String, password: String): CommonResponse<Nothing> {
        return remoteDataSource.login(username, password)
    }

    override suspend fun saveUserInfo(userInfo: UserInfo) {
        localDataSource.saveUserInfo(userInfo)
    }

    override fun getRecommendData(): Flow<PagingData<Article>> = pageDataSource.getRecommendData()
    override fun getWenDaData(): Flow<PagingData<Article>> = pageDataSource.getWenDaData()
    override fun getProjectData(): Flow<PagingData<Article>> = pageDataSource.getProjectData()

    override fun getHomeBanner(): Flow<List<Banner>> =
        remoteDataSource.getHomeBanner()
}
