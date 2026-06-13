package io.droidevs.wallpaper.data.local.dao


import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import io.droidevs.wallpaper.data.local.EffectEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface EffectDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(effect: EffectEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(effects: List<EffectEntity>)

    @Update
    suspend fun update(effect: EffectEntity)

    @Delete
    suspend fun delete(effect: EffectEntity)

    @Query("DELETE FROM effects")
    suspend fun clearAll()

    @Query("SELECT * FROM effects WHERE id = :id")
    suspend fun getById(id: Int): EffectEntity?

    // ✅ PagingSource for pagination
    @Query("SELECT * FROM effects ORDER BY id ASC LIMIT :limit OFFSET :offset")
    fun getPagedEffects(offset: Int, limit: Int): Flow<List<EffectEntity>>
}
