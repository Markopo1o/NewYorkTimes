package hu.jm.newyorktimes.di.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.jm.newyorktimes.datasource.network.TimesNetworkDataSource
import hu.jm.newyorktimes.network.TimesAPI
import hu.jm.newyorktimes.repository.NetworkRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkRepositoryModule {

    @Provides
    @Singleton
    fun provideTimesNetworkDataSource(timesAPI: TimesAPI): TimesNetworkDataSource {

        return TimesNetworkDataSource(timesAPI)
    }

    @Singleton
    @Provides
    fun provideNetworkRepository(
        timesNetworkDataSource: TimesNetworkDataSource
    ): NetworkRepository {

        return NetworkRepository(timesNetworkDataSource)
    }
}