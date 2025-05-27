package com.rensystem.p3_cinecode.core.di
import com.rensystem.p3_cinecode.feature.homeFt.data.MovieHomeRepositoryImpl
import com.rensystem.p3_cinecode.core.shareFakeData.MovieLocalService
import com.rensystem.p3_cinecode.core.shareFakeData.SessionLocalService
import com.rensystem.p3_cinecode.feature.homeFt.domain.MovieHomeRepository
import com.rensystem.p3_cinecode.feature.moviesFt.data.MovieRepositoryImpl
import com.rensystem.p3_cinecode.feature.moviesFt.data.network.MovieService
import com.rensystem.p3_cinecode.feature.moviesFt.domain.MovieRepository
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
object MovieHomeModule {

    @Provides
    @Singleton
    fun provideMovieHomeRepository(
        movieLocalService: MovieLocalService
    ): MovieHomeRepository {
        return MovieHomeRepositoryImpl(movieLocalService)
    }

    @Provides
    @Singleton
    fun provideMovieRepository(
        movieLocalService: MovieLocalService,
        theaterLocalService: TheaterLocalService,
        sessionLocalService: SessionLocalService,
        movieService: MovieService,
    ): MovieRepository {
        return MovieRepositoryImpl(
           movieLocalService = movieLocalService,
            theaterLocalService = theaterLocalService,
            sessionLocalService = sessionLocalService,
            api = movieService
        )
    }

}