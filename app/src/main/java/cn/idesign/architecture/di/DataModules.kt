package cn.idesign.architecture.di

import android.content.Context
import cn.idesign.architecture.data.source.DataRepository
import cn.idesign.architecture.data.source.DefaultDataRepository
import cn.idesign.architecture.data.source.LocalDataSource
import cn.idesign.architecture.data.source.RemoteDataSource
import cn.idesign.architecture.data.source.local.AppDatabase
import cn.idesign.architecture.data.source.local.LocalDataSourceImpl
import cn.idesign.architecture.data.source.remote.RemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class AppRemoteDataSource

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class AppLocalDataSource

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideTasksRepository(
        @AppRemoteDataSource remoteDataSource: RemoteDataSource,
        @AppLocalDataSource localDataSource: LocalDataSource,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): DataRepository {
        return DefaultDataRepository(remoteDataSource, localDataSource, ioDispatcher)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Singleton
    @AppRemoteDataSource
    @Provides
    fun provideRemoteDataSource(): RemoteDataSource = RemoteDataSourceImpl

    @Singleton
    @AppLocalDataSource
    @Provides
    fun provideLocalDataSource(
        database: AppDatabase,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): LocalDataSource {
        return LocalDataSourceImpl(database.userDao(), ioDispatcher)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }
}
