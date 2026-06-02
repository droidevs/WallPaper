package io.droidevs.wallpaper.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.droidevs.wallpaper.data.local.RemoteKeyEntity


@Dao
interface RemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKeyEntity>)

    @Query("Select * From remote_key Where dataId = :id")
    suspend fun getRemoteKeyByDataId(id: String): RemoteKeyEntity?

    @Query("Delete From remote_key")
    suspend fun clear()

    @Query("Select createdAt From remote_key Order By createdAt DESC LIMIT 1")
    suspend fun getCreationTime(): Long?
}