package com.solo.runtracker.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface RunDatabaseDao {
    @Insert
    suspend fun insert(run: Run)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(run: Run)

    @Query("SELECT * from run_table WHERE runId = :key")
    fun get(key: Long): Run?

    @Query("DELETE FROM run_table")
    suspend fun clear()

    @Query("SELECT * FROM run_table ORDER BY runId DESC LIMIT 1")
    fun getLastRun(): LiveData<Run?>

    @Query("SELECT * FROM run_table ORDER BY runId DESC LIMIT 1")
    suspend fun getRecentRun(): Run?

    @Query("SELECT * FROM run_table ORDER BY runId DESC")
    fun getAllRuns(): LiveData<List<Run?>>

}