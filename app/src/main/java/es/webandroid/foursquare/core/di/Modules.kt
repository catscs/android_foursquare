package es.webandroid.foursquare.core.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import es.webandroid.foursquare.core.connectivity.base.ConnectivityProvider
import es.webandroid.foursquare.data.repository.RepositoryImpl
import es.webandroid.foursquare.data.source.LocalDataSource
import es.webandroid.foursquare.data.source.RemoteDataSource
import es.webandroid.foursquare.domain.repository.Repository
import es.webandroid.foursquare.domain.use_cases.VenueDetailUseCase
import es.webandroid.foursquare.domain.use_cases.VenuesUseCase
import es.webandroid.foursquare.framework.local.RoomDataSource
import es.webandroid.foursquare.framework.local.base.VenueDao
import es.webandroid.foursquare.framework.local.base.VenueDatabase
import es.webandroid.foursquare.framework.remote.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun venuesService(): NetworkApi = NetworkServiceProvider().networkApi()
}

@Module
@InstallIn(SingletonComponent::class)
class ConnectivityModule {
    @Provides
    @Singleton
    fun connectivityProvider(@ApplicationContext context: Context): ConnectivityProvider =
        ConnectivityProvider.createProvider(context)
}

@Module
@InstallIn(SingletonComponent::class)
class UseCasesModule {
    @Provides
    fun provideVenuesUseCase(repository: Repository): VenuesUseCase =
        VenuesUseCase(repository)

    @Provides
    fun provideVenueDetailUseCase(repository: Repository): VenueDetailUseCase =
        VenueDetailUseCase(repository)
}

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideRepository(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource
    ): Repository = RepositoryImpl(remoteDataSource, localDataSource)
}

@Module
@InstallIn(SingletonComponent::class)
object FrameworkModule {

    @Provides
    @Singleton
    fun venuesRemoteDataSource(
        networkApi: NetworkApi,
        venueMapper: VenueMapper,
        venueDetailMapper: VenueDetailMapper
    ): RemoteDataSource = VenuesRemoteDataSource(networkApi, venueMapper, venueDetailMapper)

    @Provides
    @Singleton
    fun provideVenueDatabase(@ApplicationContext context: Context): VenueDatabase =
        VenueDatabase.build(context)

    @Provides
    @Singleton
    fun provideRoomDataSource(venueDB: VenueDatabase): LocalDataSource = RoomDataSource(venueDB)

    @Provides
    fun provideVenueDao(venueDB: VenueDatabase): VenueDao = venueDB.venueDao()
}

@Module
@InstallIn(SingletonComponent::class)
class MappersModule {
    @Provides
    @Singleton
    fun venueMapper() = VenueMapper()

    @Provides
    @Singleton
    fun venueDetailsMapper() = VenueDetailMapper()
}




