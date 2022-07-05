package cn.idesign.architecture.data.source.pagesource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import cn.chinaunicom.drone.data.source.api.ApiService
import cn.chinaunicom.drone.data.source.pagesource.PageKeyedRemoteMediator
import cn.chinaunicom.drone.data.source.pagesource.ProjectPagingSource
import cn.chinaunicom.drone.data.source.pagesource.WenDaPagingSource
import cn.idesign.architecture.data.db.AppDatabase
import cn.idesign.architecture.data.source.PageDataSource
import cn.idesign.architecture.data.vo.Article
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class PageDataSourceImpl internal constructor(
    private val service: ApiService,
    private val db: AppDatabase,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : PageDataSource {

    @OptIn(ExperimentalPagingApi::class)
    override fun getRecommendData(): Flow<PagingData<Article>> = Pager(
        config = config,
        remoteMediator = PageKeyedRemoteMediator(service, db),
    ) {
        db.articleDao().get()
    }.flow

    override fun getWenDaData(): Flow<PagingData<Article>> = Pager(
        config = config,
    ) {
        WenDaPagingSource(service)
    }.flow

    override fun getProjectData(): Flow<PagingData<Article>> = Pager(
        config = config,
    ) {
        ProjectPagingSource(service)
    }.flow

    companion object {
        val PAGE_SIZE = 10
        val config = PagingConfig(
            pageSize = PAGE_SIZE
        )
    }
}