package com.solo.runtracker.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "run_table")
data class Run(
    @PrimaryKey(autoGenerate = true)
    var runId: Long = 0L,

    @ColumnInfo(name = "elapsed_time")
    var elapsedTime: String = "",

    @ColumnInfo(name = "run_feedback")
    var runFeedback: Int = -1,

    @ColumnInfo(name = "start_time_milli")
    val startTimeMilli: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "end_time_milli")
    var endTimeMilli: Long = startTimeMilli
)