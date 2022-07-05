package cn.idesign.architecture.data.source

import androidx.paging.PagingData
import cn.idesign.architecture.data.vo.Article
import kotlinx.coroutines.flow.Flow

interface PageDataSource {
    fun getRecommendData(): Flow<PagingData<Article>>
    fun getWenDaData(): Flow<PagingData<Article>>
    fun getProjectData(): Flow<PagingData<Article>>
}