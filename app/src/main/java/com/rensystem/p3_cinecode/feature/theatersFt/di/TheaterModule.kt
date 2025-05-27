package com.rensystem.p3_cinecode.feature.theatersFt.di

import com.rensystem.p3_cinecode.core.shareFakeData.MovieLocalService
import com.rensystem.p3_cinecode.core.shareFakeData.SessionLocalService
import com.rensystem.p3_cinecode.feature.theatersFt.data.TheaterLocalService
import com.rensystem.p3_cinecode.feature.theatersFt.data.TheaterRepositoryImpl
import com.rensystem.p3_cinecode.feature.theatersFt.domain.TheaterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TheaterModule {

    @Provides
    @Singleton
    fun provideTheaterRepository(
        movieLocalService: MovieLocalService,
        theaterLocalService: TheaterLocalService,
        sessionLocalService: SessionLocalService
    ): TheaterRepository{
        return TheaterRepositoryImpl(
            theaterLocalService = theaterLocalService,
            movieLocalService = movieLocalService,
            sessionLocalService = sessionLocalService
        )
    }

}