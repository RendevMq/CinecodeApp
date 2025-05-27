package com.rensystem.p3_cinecode.feature.homeFt.data

import com.rensystem.p3_cinecode.core.shareFakeData.MovieLocalService
import com.rensystem.p3_cinecode.feature.homeFt.domain.MovieHomeRepository
import com.rensystem.p3_cinecode.feature.homeFt.domain.model.MovieHomeItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class MovieHomeRepositoryImpl @Inject constructor(
    private val movieLocalService: MovieLocalService
) : MovieHomeRepository {
    override suspend fun getNewReleaseMovies(): Flow<List<MovieHomeItem>> {
        val moviesList = movieLocalService.getAllLocalMovies().movies
            ?: emptyList()

        val newReleaseItems = moviesList
            .filter { it.isNewRelease == true }
            .map { it.toHomeDomain() }

        return flowOf(newReleaseItems)
    }
}

//fun getMoviesFromFirestore(): Flow<List<MovieItem>> = callbackFlow {
//    val listener = firestore.collection("movies")
//        .addSnapshotListener { snapshot, error ->
//            if (error != null || snapshot == null) {
//                close(error ?: Exception("Snapshot nulo"))
//                return@addSnapshotListener
//            }
//
//            val movies = snapshot.documents.mapNotNull { it.toObject(MovieData::class.java)?.toDomain() }
//            trySend(movies)
//        }
//
//    awaitClose { listener.remove() }
//}
