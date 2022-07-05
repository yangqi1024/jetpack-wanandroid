package cn.chinaunicom.drone.data.source.pagesource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import cn.chinaunicom.drone.data.source.api.ApiService
import cn.idesign.architecture.data.vo.Article

class ProjectPagingSource(private val apiService: ApiService) : PagingSource<Int, Article>() {
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val page = params.key ?: 1
            val repoResponse = apiService.getProjectList(page, 294)
            val repoItems = repoResponse.data.datas
            val prevKey = if (page > 1) page - 1 else null
            val nextKey = if (repoItems.isNotEmpty()) page + 1 else null
            LoadResult.Page(repoItems, prevKey, nextKey)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}
