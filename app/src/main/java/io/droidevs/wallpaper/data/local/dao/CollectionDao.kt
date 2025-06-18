package io.droidevs.wallpaper.data.local.dao

import android.database.sqlite.SQLiteConstraintException
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Delete
import androidx.room.Transaction
import androidx.room.Update
import io.droidevs.wallpaper.data.local.CollectionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CollectionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCollection(collection: CollectionEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(collections: List<CollectionEntity>) : List<Long>


    @Query("SELECT * FROM collections WHERE remote_id = :id")

    suspend fun getCollectionById(id: Int): Flow<CollectionEntity?>

    @Query("SELECT * FROM collections LIMIT :limit OFFSET :offset")
    suspend fun getCollectionsPaged(offset: Int, limit: Int): Flow<List<CollectionEntity>>


    @Update
    suspend fun updateCollection(collection: CollectionEntity)

    @Delete
    suspend fun deleteCollection(collection: CollectionEntity): Int

    @Query("DELETE FROM collections WHERE remote_id = :id")
    suspend fun deleteCollectionById(id: Int) : Int

    @Query("DELETE FROM collections")
    suspend fun clearAllCollections() : Int
}
