package cn.chinaunicom.drone.data.source.pagesource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import cn.chinaunicom.drone.data.source.api.ApiService
import cn.idesign.architecture.data.db.AppDatabase
import cn.idesign.architecture.data.vo.Article
import cn.idesign.architecture.data.vo.RemoteArticleKeys


private const val STARTING_PAGE_INDEX = 0

@OptIn(ExperimentalPagingApi::class)
class PageKeyedRemoteMediator(
    val api: ApiService,
    val db: AppDatabase,
) : RemoteMediator<Int, Article>() {
    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Article>
    ): MediatorResult {
        try {
            // Get the closest item from PagingState that we want to load data around.
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevKey = remoteKeys?.prevKey
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    prevKey
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextKey = remoteKeys?.nextKey
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    nextKey
                }
            }
            println("--->page:${page},loadType:${loadType}")
            val result = api.getHomeArticle(page)
            val eventList = result.data.datas
            val endOfPaginationReached = eventList.isEmpty()
            db.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    db.remoteArticleKeysDao().clearRemoteKeys()
                    db.articleDao().clear()
                }
                val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = eventList.map {
                    RemoteArticleKeys(articleId = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                db.remoteArticleKeysDao().insertAll(keys)
                db.articleDao().insert(eventList)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Article>): RemoteArticleKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull() {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()
            ?.let { event ->
                // Get the remote keys of the last item retrieved
                db.remoteArticleKeysDao().remoteKeysArticleId(event.id)
            }
    }


    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Article>
    ): RemoteArticleKeys? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { eventId ->
                db.remoteArticleKeysDao().remoteKeysArticleId(eventId)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Article>): RemoteArticleKeys? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { event ->
                // Get the remote keys of the first items retrieved
                db.remoteArticleKeysDao().remoteKeysArticleId(event.id)
            }
    }
}
