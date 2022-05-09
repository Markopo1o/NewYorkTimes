package hu.jm.newyorktimes.di.disk

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hu.jm.newyorktimes.datasource.disk.NewsDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        NewsDatabase::class.java,
        "news_table"
    ).build()

    @Singleton
    @Provides
    fun provideDao(newsDatabase: NewsDatabase) = newsDatabase.newsDao()

}