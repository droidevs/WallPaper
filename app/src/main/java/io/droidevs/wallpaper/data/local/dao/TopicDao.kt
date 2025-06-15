package io.droidevs.wallpaper.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import io.droidevs.wallpaper.data.local.TopicEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TopicDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopic(topic: TopicEntity) : Long


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(topics: List<TopicEntity>) : List<Long>


    @Query("DELETE FROM topics")
    suspend fun clearAll() : Int

    @Query("SELECT * FROM topics ORDER BY published_at DESC")
    fun getAllTopics(): Flow<List<TopicEntity>>

    @Query("SELECT * FROM topics WHERE id = :id")
    suspend fun getTopicById(id: String): Flow<TopicEntity?>

    @Query("SELECT * FROM topics ORDER BY id LIMIT :limit OFFSET :offset")
    suspend fun getTopics(offset: Int, limit: Int) : Flow<List<TopicEntity>>

}